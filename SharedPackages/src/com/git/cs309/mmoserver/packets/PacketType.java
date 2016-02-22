package com.git.cs309.mmoserver.packets;

public enum PacketType {
	TEST_PACKET((byte) 6), ENTITY_UPDATE_PACKET((byte) 5), EVENT_PACKET((byte) 4), ERROR_PACKET((byte) 3), LOGIN_PACKET(
			(byte) 2), MESSAGE_PACKET((byte) 1), NULL_PACKET((byte) 0);
	
	public static final byte NULL_PACKET_BYTE = 0;
	public static final byte MESSAGE_PACKET_BYTE = 1;
	public static final byte LOGIN_PACKET_BYTE = 2;
	public static final byte ERROR_PACKET_BYTE = 3;
	public static final byte EVENT_PACKET_BYTE = 4;
	public static final byte ENTITY_UPDATE_PACKET_BYTE = 5;
	public static final byte TEST_PACKET_BYTE = 6;
	private final byte typeByte;

	private PacketType(final byte typeByte) {
		this.typeByte = typeByte;
	}

	public final byte getTypeByte() {
		return typeByte;
	}
}
