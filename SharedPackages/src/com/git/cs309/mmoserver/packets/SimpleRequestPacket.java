package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class SimpleRequestPacket extends Packet {
	public static final byte GET_INVENTORY = 1;
	public static final byte GET_EQUIPMENT = 2;
	public static final byte GET_STATS = 3;
	public static final byte GET_SKILLS = 4;
	private final byte request;

	public SimpleRequestPacket(AbstractConnection connection, byte request) {
		super(connection);
		this.request = request;
	}

	public SimpleRequestPacket(AbstractConnection connection, byte[] bytes) {
		super(connection);
		this.request = bytes[1];
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.SIMPLE_REQUEST_PACKET;
	}

	public byte getRequest() {
		return request;
	}

	@Override
	public int sizeOf() {
		return 2;
	}

	@Override
	public byte[] toBytes() {
		return new byte[] { getPacketType().getTypeByte(), request };
	}

}
