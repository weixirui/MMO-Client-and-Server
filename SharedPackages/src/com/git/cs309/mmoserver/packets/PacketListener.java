package com.git.cs309.mmoserver.packets;

import java.util.concurrent.TimeoutException;

public final class PacketListener {
	public static final long DEFAULT_TIMEOUT = 5000; // 5 seconds
	private final PacketType desiredPacketType;
	private final long timeout;
	private volatile Packet packet = null;

	public PacketListener(final PacketType desiredPacketType) {
		this.desiredPacketType = desiredPacketType;
		this.timeout = DEFAULT_TIMEOUT;
	}

	public PacketListener(final PacketType desiredPacketType, final long timeout) {
		this.desiredPacketType = desiredPacketType;
		this.timeout = timeout;
	}

	public final PacketType getDesiredPacketType() {
		return desiredPacketType;
	}

	public final Packet getPacket() throws TimeoutException {
		while (packet == null) {
			synchronized (this) {
				try {
					this.wait(timeout);
				} catch (InterruptedException e) {
					continue;
				}
				break;
			}
		}
		if (packet == null) {
			throw new TimeoutException("Packet listener timed out before getting packet.");
		}
		return packet;
	}

	public final long getTimeout() {
		return timeout;
	}

	public final void setPacket(Packet packet) {
		this.packet = packet;
	}
}
