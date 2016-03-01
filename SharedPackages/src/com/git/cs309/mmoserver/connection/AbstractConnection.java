package com.git.cs309.mmoserver.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;

import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.packets.PacketFactory;
import com.git.cs309.mmoserver.util.CorruptDataException;
import com.git.cs309.mmoserver.util.CycleQueue;
import com.git.cs309.mmoserver.util.EndOfStreamReachedException;
import com.git.cs309.mmoserver.util.StreamUtils;

public abstract class AbstractConnection extends Thread {
	protected final OutputStream output;
	protected final InputStream input;
	protected final Socket socket;
	protected final String ip;
	protected volatile boolean disconnected = false;
	protected volatile boolean closeRequested = false;
	protected volatile Queue<Packet> incommingPackets = new CycleQueue<>(50); // Making this volatile should allow for other threads to access it properly, as well as be changed by this thread properly.
	protected volatile Queue<Packet> outgoingPackets = new CycleQueue<>(50);

	protected volatile Thread outgoingThread = new Thread() {
		@Override
		public void run() {
			setName(AbstractConnection.this + "'s Output Thread");
			while (!isClosed()) {
				synchronized (outgoingPackets) {
					while (!disconnected && outgoingPackets.size() > 0) {
						try {
							StreamUtils.writeBlockToStream(output, outgoingPackets.remove().toBytes());
						} catch (SocketException e) {
							System.err.println("Connection disconnected while writing to stream");
							closeRequested = true;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						outgoingPackets.wait();
					} catch (InterruptedException e) {
						//Don't care if it gets interrupted.
					}
				}
			}
		}
	};

	//Throwing IOException because if the exception occurs here, there's not much we can really do.
	//It's better to let the ConnectionAcceptor try and handle this issue.
	public AbstractConnection(final Socket socket) throws IOException {
		this.socket = socket;
		output = socket.getOutputStream();
		input = socket.getInputStream();
		ip = socket.getInetAddress().getHostAddress();
		setName(toString());
		start();
		outgoingThread.start();
	}

	public void addOutgoingPacket(Packet packet) {
		synchronized (outgoingPackets) {
			outgoingPackets.add(packet);
			outgoingPackets.notifyAll();
		}
	}

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
		outgoingPackets.notifyAll(); // Make sure thread kills itself.
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof AbstractConnection && ((AbstractConnection) other).ip.equals(ip);
	}

	public void forceOutgoingPacket(final Packet packet) throws IOException {
		StreamUtils.writeBlockToStream(output, packet.toBytes());
	}

	public String getIP() {
		return ip;
	}

	//Since packet is volatile, shouldn't need synchronized method block.
	public Packet getPacket() {
		synchronized (incommingPackets) {
			if (incommingPackets.isEmpty()) {
				return null;
			}
			return incommingPackets.remove();
		}
	}

	public abstract void handlePacket(Packet packet);

	public boolean isClosed() {
		return socket.isClosed() || closeRequested;
	}

	public boolean isDisconnected() {
		return disconnected;
	}

	public abstract void iterationStartBlock();

	public abstract int maxPacketsPerIteration();

	public abstract void postRun();

	@Override
	public final void run() {
		int packetsThisTick;
		//ConnectionManager singleton to wait on.
		while (!isClosed()) {
			iterationStartBlock();
			synchronized (incommingPackets) {
				incommingPackets.clear();
			}
			packetsThisTick = 0;
			try {
				do {
					try {
						Packet packet = PacketFactory.buildPacket(StreamUtils.readBlockFromStream(input), this);
						synchronized (incommingPackets) {
							incommingPackets.add(packet);
						}
						handlePacket(packet);
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
					if (++packetsThisTick == maxPacketsPerIteration()) {
						System.out.println(
								this + " exceeded the maximum packets per tick limit. Packets: " + packetsThisTick);
						closeRequested = true;
						break;
					}
				} while (!isClosed() && input.available() > 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		postRun();
		close();
		disconnected = true;
	}

	@Override
	public String toString() {
		return "Connection: " + ip;
	}
}
