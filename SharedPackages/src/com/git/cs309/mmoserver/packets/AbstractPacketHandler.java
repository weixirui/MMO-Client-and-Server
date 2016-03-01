package com.git.cs309.mmoserver.packets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public abstract class AbstractPacketHandler {
	private final Set<PacketListener> listenerSet = new HashSet<>();

	public final Packet getResponse(final Packet packet, final PacketType desiredPacketType) throws TimeoutException {
		packet.send();
		PacketListener listener = new PacketListener(desiredPacketType);
		listenerSet.add(listener);
		return listener.getPacket();
	}

	public final Packet getResponse(final Packet packet, final PacketType desiredPacketType, final long timeout)
			throws TimeoutException {
		packet.send();
		PacketListener listener = new PacketListener(desiredPacketType, timeout);
		listenerSet.add(listener);
		return listener.getPacket();
	}

	public final void handlePacket(Packet packet) {
		List<PacketListener> toRemove = new ArrayList<>();
		for (PacketListener listener : listenerSet) {
			if (listener.getDesiredPacketType() == packet.getPacketType()) {
				listener.setPacket(packet);
				synchronized (listener) {
					listener.notifyAll();
				}
				toRemove.add(listener);
			}
		}
		listenerSet.removeAll(toRemove);
		handlePacketBlock(packet);
	}

	public abstract void handlePacketBlock(Packet packet);
}
