package com.git.cs309.mmoserver.util;

import java.util.Observable;

import javax.swing.JButton;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.connection.ConnectionManager;
import com.git.cs309.mmoserver.entity.characters.user.Rights;
import com.git.cs309.mmoserver.packets.ServerModuleStatusPacket;

/**
 * 
 * @author Group 21
 * 
 *         <p>
 *         TickProcess is the skeleton of the Server, or rather it's
 *         implementations are. TickProcess is a class which executes every tick
 *         and performs a task. Ideally, this class is to be used for processes
 *         which will happen throughout the duration of the server's lifetime.
 *         If not, consider using CycleProcesses, as they're much less resource
 *         intensive.
 *         </p>
 */
public abstract class TickProcess extends Observable implements Runnable {
	protected volatile long average = 0;
	protected volatile int count = 0;
	protected volatile long cumulative = 0;
	protected volatile boolean forceStop = false;
	protected volatile boolean isStopped = true;
	protected final String name;
	protected final JButton restartButton = new JButton("Restart");
	protected volatile boolean tickFinished = true;
	protected volatile Thread TickProcessThread = null;

	public TickProcess(final String name) {
		this.name = name;
		Main.addTickProcess(this);
		start();
	}

	public abstract void ensureSafeClose();

	public final void forceStop() {
		forceStop = true;
	}

	/**
	 * Allows access to the average time per tick of this object.
	 * 
	 * @return the average tick time.
	 */
	public final long getAverageTick() {
		return average;
	}

	/**
	 * Is this process stopped?
	 * 
	 * @return
	 */
	public final boolean isStopped() {
		return isStopped;
	}

	public abstract void printStatus();

	@Override
	public final void run() { // Final to ensure that this can't be overriden, to ensure that all extending classes follow the rules.
		final Object tickNotifier = Main.getTickNotifier(); // Acquire the tickNotifier object from Main.
		isStopped = false;
		forceStop = false;
		println("Running " + this + "...");
		while (Main.isRunning() || !forceStop) { // While server is running...
			try {
				synchronized (tickNotifier) {
					try {
						tickNotifier.wait(); // Wait for tick notification.
					} catch (InterruptedException e) {
						// We don't care too much if it gets interrupted.
					}
					tickFinished = false;
				}
				setChanged();
				notifyObservers();
				long start = System.nanoTime();
				tickTask(); // Perform task
				handleTickAveraging(System.nanoTime() - start);
				tickFinished = true;
				setChanged();
				notifyObservers();
			} catch (Exception e) {
				println("Here");
				e.printStackTrace();
				break;
			}
		}
		ensureSafeClose();
		tickFinished = true;
		isStopped = true;
		setChanged();
		notifyObservers();
		println(this + " has stopped running.");
	}

	public final void start() {
		if (TickProcessThread == null || !TickProcessThread.isAlive()) {
			TickProcessThread = new Thread(this);
			TickProcessThread.setName(name);
			TickProcessThread.start();
		}
	}

	public final boolean tickFinished() {
		return tickFinished;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Handles tick averaging.
	 * 
	 * @param thisTick
	 *            time this tick
	 */
	protected final void handleTickAveraging(long thisTick) {
		cumulative += thisTick;
		count++;
		if (count == 10) {
			average = cumulative / count;
			count = 0;
			cumulative = 0;
			ConnectionManager.getInstance().sendPacketToConnectionsWithRights(
					new ServerModuleStatusPacket(null, name, average / (Config.MILLISECONDS_PER_TICK * 1000000.0f)),
					Rights.ADMIN);
		}
	}

	protected final void println(String message) {
		System.out.println("[" + this + "] " + message);
	}

	protected abstract void tickTask();
}
