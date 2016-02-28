package com.git.cs309.mmoserver.connection;

import java.io.IOException;
import java.net.Socket;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.entity.characters.user.User;
import com.git.cs309.mmoserver.entity.characters.user.UserManager;
import com.git.cs309.mmoserver.packets.PacketFactory;
import com.git.cs309.mmoserver.util.ClosedIDSystem;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;
import com.git.cs309.mmoserver.util.CorruptDataException;
import com.git.cs309.mmoserver.util.EndOfStreamReachedException;
import com.git.cs309.mmoserver.util.StreamUtils;

/**
 * 
 * @author Group 21
 *
 *         <p>
 *         The connection object encapsulates each socket, and handles I/O
 *         between the server and the client.
 *         </p>
 */
public class Connection extends AbstractConnection {
	//Whether or not the Connection should try and close
	private volatile boolean closeRequested = false;
	//Whether or not the Connection has logged into a User
	private volatile boolean loggedIn = false;
	//The User belonging to this Connection. Null until loggedIn
	private volatile User user = null;
	//The Unique ID of this connection.
	private final IDTag idTag;

	public Connection(Socket socket) throws IOException {
		super(socket);
		idTag = ClosedIDSystem.getTag(); // Automatically get an IDTag in the constructor.
	}

	/**
	 * Ensures all members of the Connection are ready for the Connection to be
	 * Garbage Collected. In this case, just returns the IDTag.
	 */
	public void cleanUp() {
		idTag.returnTag(); // return the tag to the system.
	}

	/**
	 * Closes all the I/O and the Socket for this Connection.
	 */
	@Override
	public synchronized void close() {
		try {
			if (input != null)
				input.close();
			if (output != null)
				output.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		disconnected = true;
		closeRequested = true;
		synchronized (outgoingPackets) {
			outgoingPackets.notifyAll();
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Connection && ((Connection) other).getServerSideIP().equals(getServerSideIP());
	}

	/**
	 * The ServerSide IP is just the Connections IP with an added digit at the
	 * end, the Connections IDTag number. This is so that two connections with
	 * the same IP can play at the same time, and still be distinguishable.
	 * 
	 * @return The Socket's IP with "."+idTag.getID() added onto the end.
	 */
	public String getServerSideIP() {
		return socket.getInetAddress().getHostAddress() + "." + idTag.getID();
	}

	/**
	 * The User Object for each connection is the User object that the
	 * connection logged into.
	 * 
	 * @return the User object for this connection, or null if the Connection
	 *         has not yet logged into a user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Checks whether or not this Connection has logged into a User.
	 * 
	 * @return true if loggedIn is true and user is not null, otherwise false.
	 */
	public boolean isLoggedIn() {
		return loggedIn && user != null;
	}

	@Override
	public void run() {
		int packetsThisTick;
		//ConnectionManager singleton to wait on.
		final Object waitObject = ConnectionManager.getInstance().getWaitObject();
		while (!socket.isClosed() && !closeRequested) {
			synchronized (waitObject) {
				try {
					waitObject.wait(); // Wait for connection manager to notify us of new tick.
				} catch (InterruptedException e) {
					// We shouldn't care too much if it gets interrupted.
				}
			}
			packet = null;
			packetsThisTick = 0;
			try {
				do {
					try {
						packet = PacketFactory.buildPacket(StreamUtils.readBlockFromStream(input), this);
					} catch (CorruptDataException | NegativeArraySizeException | ArrayIndexOutOfBoundsException e) { // Just general exception that might occur for bad packets.
						System.err.println(e.getMessage());
					} catch (EndOfStreamReachedException e) { // End of the stream was reached, meaning there's no more data, ever.
						System.err.println(e.getMessage());
						closeRequested = true;
						break;
					} catch (IOException e) { // Should only be Connection reset
						System.err.println(e.getMessage());
						closeRequested = true;
						break;
					}
					if (++packetsThisTick == Config.PACKETS_PER_TICK_BEFORE_KICK) {
						System.out.println(
								this + " exceeded the maximum packets per tick limit. Packets: " + packetsThisTick);
						closeRequested = true;
						break;
					}
				} while (!socket.isClosed() && input.available() != 0 && !closeRequested);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loggedIn = false;
		user = UserManager.getUserForIP(getServerSideIP());
		if (user != null) {
			UserManager.logOut(user.getUsername());
		}
		close();
		disconnected = true;
	}

	/**
	 * Sets the state of this connection's loggedIn value. This does not mean
	 * that isLoggedIn will return true, as the user object must also be set to
	 * the active user.
	 * 
	 * @param state
	 *            the new state of the boolean loggedIn
	 */
	public void setLoggedIn(boolean state) {
		loggedIn = state;
	}

	/**
	 * Sets the user object of this connection to the passed value. This does
	 * not mean that isLoggedIn will return true, as the boolean loggedIn must
	 * also be set to true.
	 * 
	 * @param user
	 *            the new User object
	 */
	public void setUser(final User user) {
		this.user = user;
	}
}
