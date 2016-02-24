package com.git.cs309.mmoserver.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.packets.ErrorPacket;

/**
 * 
 * @author Group 21
 *
 *         Connection acceptor. Waits for connections, then encapsulates the
 *         sockets into Connection containers.
 */
public final class ConnectionAcceptor implements Runnable {
	private static Thread connectionAcceptorThread;
	private static int port = 6667; // A default port.
	private static final ConnectionAcceptor SINGLETON = new ConnectionAcceptor();

	public static ConnectionAcceptor getSingleton() {
		return SINGLETON;
	}

	/**
	 * Creates a new thread that runs the singleton object, and starts it.
	 * 
	 * @param port
	 *            port to start acceptor on.
	 */
	public static void startAcceptor(final int port) {
		ConnectionAcceptor.port = port; // Set port
		connectionAcceptorThread = new Thread(SINGLETON); // New thread.
		connectionAcceptorThread.setName("ConnectionAcceptor"); // Set name so it can be identified easily.
		connectionAcceptorThread.start(); // Start thread.
	}

	private ConnectionAcceptor() {
		// Can only be instantiated internally.
	}

	/**
	 * Run method.
	 */
	@Override
	public void run() {
		ServerSocket acceptorSocket; // Declare new ServerSocket variable.
		try {
			acceptorSocket = new ServerSocket(port); // Try and create socket.
		} catch (IOException e) {
			e.printStackTrace();
			Main.requestExit(); // It failed, so might as well just exit.
			return;
		}
		if (acceptorSocket != null && !acceptorSocket.isClosed()) { // If it's not null and not closed, proceed.
			try {
				System.out.println("Acceptor running on " + InetAddress.getLocalHost() + ":" + port);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			while (Main.isRunning() && !acceptorSocket.isClosed()) { // While open and server is running..
				try {
					addConnection(new Connection(acceptorSocket.accept())); // Accept new socket, and immediately encapsulate.
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to accept new connection...");
				}
			}
		}
		try {
			if (acceptorSocket != null) {
				acceptorSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addConnection(Connection connection) throws IOException {
		//For now, allowing multiple connections from same IP
		/*
		 * if (Main.getConnectionManager().ipConnected(connection.getIP())) { //
		 * Is a socket with same IP already connected?
		 * connection.forceOutgoingPacket(new ErrorPacket(null,
		 * ErrorPacket.GENERAL_ERROR,
		 * "Failed to connect because your ip is already logged in.")); // Send
		 * error packet. connection.close(); // Close connection. return; }
		 */
		if (Main.getConnectionManager().full()) { // Are we at max connections?
			connection.forceOutgoingPacket(
					new ErrorPacket(null, ErrorPacket.GENERAL_ERROR, "Failed to connect because server is full.")); // Send error packet
			connection.close(); // Close
			return;
		}
		Main.getConnectionManager().addConnection(connection); // Made it to end, so add to manager.
	}
}
