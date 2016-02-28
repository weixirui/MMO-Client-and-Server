package com.git.cs309.mmoserver;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.git.cs309.mmoserver.connection.ConnectionAcceptor;
import com.git.cs309.mmoserver.connection.ConnectionManager;
import com.git.cs309.mmoserver.cycle.CycleProcessManager;
import com.git.cs309.mmoserver.entity.characters.CharacterManager;
import com.git.cs309.mmoserver.entity.characters.npc.NPCFactory;
import com.git.cs309.mmoserver.entity.characters.user.ModerationHandler;
import com.git.cs309.mmoserver.entity.characters.user.UserManager;
import com.git.cs309.mmoserver.entity.objects.GameObjectFactory;
import com.git.cs309.mmoserver.io.Logger;
import com.git.cs309.mmoserver.items.ItemFactory;
import com.git.cs309.mmoserver.map.MapFactory;
import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.util.TickProcess;

/*
 * TODO Section:
 * Implement party system
 * Fix Maps so that more than one Entity can exist on same tile
 * Add more packet outputs for various things
 * Add more packets to output MORE various things
 * Add and send map packet, which tells client player is in new map
 */

/**
 * 
 * @author Group 21
 *
 *         Main is the main class and entry point of the MMOServer. I also
 *         handles the "tick" mechanics.
 *
 *         <p>
 *         A tick in this server framework is simply a notification to all the
 *         threads running TickProcess objects telling them to wake up from
 *         their wait call. All tick reliants invoke <code>wait()</code> on the
 *         TICK_NOTIFIER object, which they retrieve from this class. When a
 *         "tick" is sent out, <code>notifyAll()</code> is called on
 *         TICK_NOTIFIER, which in turn allows threads to exit wait and begin
 *         execution of their tick procedures. Once a tick begins, the main
 *         thread (the one used during entry into this class, which also handles
 *         ticking) waits until all TickProcess objects have finished their tick
 *         procedures. The main thread then waits out any remaining time, then
 *         notifies all the threads waiting on TICK_NOTIFIER to start a new tick
 *         cycle, infinitely repeating.
 *         </p>
 * 
 *         <p>
 *         Another neat feature is that whenever a TickProcess thread fails
 *         because of an uncaught exception, the whole server pauses (after the
 *         current tick) so that debugging may commence. From there, the thread
 *         can actually be restarted from a button in the GUI.
 *         </p>
 */
public final class Main {

	// Is server running.
	private static volatile boolean running = true;

	// Object that all TickProcess objects wait on for tick notification.
	private static final Object TICK_NOTIFIER = new Object(); // To notify

	// List of TickProcess objects currently running. They add themselves to
	// this list when instantiated.
	private static final List<TickProcess> TICK_RELIANT_LIST = new ArrayList<>();

	// threads of
	// new tick.
	// Current server ticks count.
	private static volatile long tickCount = 0; // Tick count.

	/**
	 * Can be used to register tick reliants so that server will know to wait
	 * until theyre finished. Automatically invoked from TickProcess contructor.
	 * 
	 * @param TickProcess
	 *            new object to register in list.
	 */
	public static void addTickProcess(final TickProcess TickProcess) {
		synchronized (TICK_RELIANT_LIST) { // Synchronize block to obtain
											// TICK_RELIANT_LIST lock
			TICK_RELIANT_LIST.add(TickProcess);
		}
	}

	/**
	 * Getter method for tickCount.
	 * 
	 * @return current tickCount
	 */
	public static long getTickCount() {
		return tickCount;
	}

	/**
	 * Getter method for TICK_NOTIFIER.
	 * 
	 * @return the TICK_NOTIFIER object.
	 */
	public static Object getTickNotifier() {
		return TICK_NOTIFIER;
	}

	/**
	 * Getter for running state.
	 * 
	 * @return running state
	 */
	public static boolean isRunning() {
		return running;
	}

	//Turns out that using the default system loader will just re-reference already loaded classes. Would need to create and use a different classloader
	//Will do, if I can find time to do something ridiculous like that. Keeping them like this for time being (not singletons, that is)

	/**
	 * Main method, duh.
	 * 
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		System.setOut(Logger.getOutPrintStream()); // Set System out to logger
													// out
		System.setErr(Logger.getErrPrintStream());
		Runtime.getRuntime().addShutdownHook(new Thread() { // Add shutdown hook
															// that autosaves
															// users.
			@Override
			public void run() {
				saveEverything();
				System.out.println("Saved all users before going down.");
			}
		});
		ConnectionAcceptor.startAcceptor(43594);
		while (true) {
			runServer();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// Don't care tooo much if it gets interrupted.
			}
			System.out.println("Restarting server...");
		}
	}

	/**
	 * Requests program termination.
	 */
	public static void requestExit() {
		running = false;
	}

	/**
	 * Initializes handler and managers, to ensure they're ready to handle
	 * activity.
	 */
	private static void loadAndStartClasses() {
		ModerationHandler.loadModerations();
		try {
			NPCFactory.getInstance().loadDefinitions();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			GameObjectFactory.getInstance().loadDefinitions();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			ItemFactory.getInstance().loadDefinitions();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		MapFactory.getInstance();
		MapHandler.getInstance().loadMaps();
		ConnectionManager.getInstance();
		CycleProcessManager.getInstance();
		CharacterManager.getInstance();
	}

	private static void runServer() {
		running = true;
		loadAndStartClasses(); // Call initialize block, which will initialize things
		// that should be initialized before starting server.
		System.out.println("Starting server...");
		int ticks = 0;
		long tickTimes = 0L;
		while (running) {
			long start = System.currentTimeMillis();
			synchronized (TICK_NOTIFIER) { // Obtain intrinsic lock and notify
											// all waiting threads. This starts
											// the tick.
				TICK_NOTIFIER.notifyAll();
			}
			boolean allFinished;
			do { // This block keeps looping until all tick reliant threads are
					// finished with their tick.
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// Don't really care too much if it gets interrupted.
				}
				allFinished = true;
				for (TickProcess t : TICK_RELIANT_LIST) {
					if (t.isStopped()) { // Uncaught exception or SOMETHING
						t.start(); // Automatically restart.
						break;
					}
					if (!t.tickFinished()) {
						allFinished = false;
						break;
					}
				}
			} while (!allFinished);
			long timeLeft = Config.MILLISECONDS_PER_TICK - (System.currentTimeMillis() - start); // Calculate
																									// remaining
																									// time.
			tickTimes += (System.currentTimeMillis() - start);
			ticks++;
			tickCount++;
			if (ticks == Config.TICKS_PER_MINUTE * Config.STATUS_PRINT_RATE) {
				System.out.println(" ");
				System.out.println("Average tick consumption over " + Config.STATUS_PRINT_RATE + " minutes: "
						+ String.format("%.3f", ((tickTimes / (float) (Config.MILLISECONDS_PER_TICK * ticks))) * 100.0f)
						+ "%.");
				for (TickProcess process : TICK_RELIANT_LIST) {
					process.printStatus();
				}
				System.out.println(" ");
				ticks = 0;
				tickTimes = 0L;
			}
			//if (timeLeft < 0) {
			//	System.err.println("Warning: Server is lagging behind desired tick time " + (-timeLeft) + "ms.");
			//}
			if (timeLeft < 2) {
				timeLeft = 2; // Must wait at least a little bit, so that
								// threads can catch up and wait.
			}
			try {
				Thread.sleep(timeLeft);
			} catch (InterruptedException e) {
				// Don't really care too much if it gets interrupted.
			}
		}
		System.out.println("Server going down...");
		saveEverything();
		System.out.println("Saved all users before going down.");
		System.out.println("");
		System.out.println("");
	}

	private static void saveEverything() {
		UserManager.saveAllUsers();
		ModerationHandler.saveModerations();
	}
}
