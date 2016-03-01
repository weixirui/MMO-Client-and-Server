package com.git.cs309.mmoserver.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.entity.characters.user.Rights;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.packets.PacketHandler;
import com.git.cs309.mmoserver.packets.PacketType;
import com.git.cs309.mmoserver.util.TickProcess;

/**
 * 
 * @author Group 21
 *
 *         ConnectionManager object, which extends TickProcess. Houses all
 *         connections, and, each tick, collects the single most recent packet
 *         from each connection And processes them with the help of
 *         PacketHandler. This is, probably, going to be the slowest of all the
 *         tick reliants, aside from NPCManager, which may turn out to be
 *         slower. Another added functionality of this class is that it has
 *         methods to obtain a connection based on connection IP. A neat feature
 *         about the design of this (due to TickProcess) is that whenever this
 *         fails, the connections do NOT get removed, and all the methods still
 *         work, so it can still take on new connections.
 */
public final class ConnectionManager extends TickProcess {
	private static final ConnectionManager INSTANCE = new ConnectionManager();

	public static final ConnectionManager getInstance() {
		return INSTANCE;
	}

	private final Map<String, Connection> connectionMap = new HashMap<>(); // Could hold both username -> connection and ip -> connection. But will probably only hold ip -> connection, since that's all that's needed.
	private final List<Connection> connections = new ArrayList<>(Config.MAX_CONNECTIONS);
	private Object waitObject = new Object();
	private long ticks;
	private long packetTotals;

	public ConnectionManager() {
		super("ConnectionManager");
	}

	/**
	 * Adds a connection to the connection list, so it can be processed over and
	 * found.
	 * 
	 * @param connection
	 *            connection to add to list.
	 */
	public void addConnection(final Connection connection) {
		synchronized (connectionMap) {
			connectionMap.put(connection.getServerSideIP(), connection); // Add connection to "IP->Connection" map
		}
		synchronized (connections) {
			connections.add(connection); // Add connection to list.
			println("Connection joined: " + connection.getServerSideIP());
		}
	}

	@Override
	public void ensureSafeClose() {
		//Not required
	}

	/**
	 * Determines whether or not the ConnectionManager is full.
	 * 
	 * @return true if max capacity has been reached, false if not.
	 */
	public synchronized boolean full() {
		return connections.size() >= Config.MAX_CONNECTIONS;
	}

	/**
	 * Gets a connection from the connection map based on the IP provided.
	 * 
	 * @param ip
	 *            IP of the connection to get.
	 * @return the connection sharing the same IP, or null if one does not
	 *         exist.
	 */
	public synchronized Connection getConnectionForIP(final String ip) {
		return connectionMap.get(ip);
	}

	public Object getWaitObject() {
		return waitObject;
	}

	/**
	 * Checks whether or not an Connection with the IP provided
	 * 
	 * @param ip
	 *            IP to check if is already connected.
	 * @return true if the connection map contains IP as a key, false if not.
	 */
	public boolean ipConnected(String ip) {
		synchronized (connectionMap) {
			return connectionMap.containsKey(ip);
		}
	}

	@Override
	public void printStatus() {
		println("Total connections: " + connections.size());
		println("Average packets per tick: " + ((float) (packetTotals / ticks)));
		ticks = 0;
		packetTotals = 0;
	}

	/**
	 * Removes a connection from the list and map.
	 * 
	 * @param connection
	 *            connection to remove.
	 */
	public void removeConnection(final Connection connection) {
		synchronized (connectionMap) {
			connectionMap.remove(connection.getServerSideIP());
		}
		synchronized (connections) {
			connections.remove(connection);
		}
		println("Connection disconnected: " + connection.getServerSideIP());
		connection.cleanUp();
	}

	/**
	 * Removes a connection from the list and map for the given IP
	 * 
	 * @param ip
	 *            IP of connection to remove
	 */
	public void removeConnection(final String ip) {
		Connection connection = getConnectionForIP(ip);
		synchronized (connectionMap) {
			connectionMap.remove(connection);
		}
		synchronized (connections) {
			connections.remove(connection);
		}
		println("Connection disconnected: " + connection.getServerSideIP());
		connection.cleanUp();
	}

	/**
	 * Sends a packet to all connections in list.
	 * 
	 * @param packet
	 *            packet to send
	 */
	public void sendPacketToAllConnections(final Packet packet) {
		synchronized (connections) {
			for (Connection connection : connections) {
				if (connection.isLoggedIn())
					connection.addOutgoingPacket(packet);
			}
		}
	}

	/**
	 * Only sends the packet to connections with rights at or above the rights
	 * specified. Admins will get all mod and player packets sent through this,
	 * and mods will only get mod and player packets. Players will only get
	 * player packets.
	 * 
	 * @param packet
	 *            packet to be sent.
	 * @param rights
	 *            rights required to recieve the packet.
	 */
	public void sendPacketToConnectionsWithRights(final Packet packet, final Rights rights) {
		synchronized (connections) {
			for (Connection connection : connections) {
				if (connection.isLoggedIn() && (connection.getUser().getRights() == Rights.ADMIN
						|| (rights == Rights.MOD && connection.getUser().getRights() != Rights.PLAYER)))
					connection.addOutgoingPacket(packet);
			}
		}
	}

	@Override
	protected void tickTask() {
		final List<Packet> packets = new ArrayList<>(connections.size()); // New list for packets.
		synchronized (connections) {
			for (int i = 0; i < connections.size(); i++) {
				if (connections.get(i).isDisconnected()) {
					removeConnection(connections.get(i--));
					continue;
				}
				Packet packet = null;
				while ((packet = connections.get(i).getPacket()) != null
						&& packet.getPacketType() != PacketType.NULL_PACKET) {
					packets.add(packet);
				}
			}
		}
		synchronized (waitObject) {
			waitObject.notifyAll(); // Since all connections are waiting on this class's singleton, notifyAll wakes them up so they can start reading packets again.
		}
		for (Packet packet : packets) {
			PacketHandler.getInstance().handlePacket(packet); // Handle all the packets.
		}
		packetTotals += packets.size();
		ticks++;
		packets.clear();
	}
}
