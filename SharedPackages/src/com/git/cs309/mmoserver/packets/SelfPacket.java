package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class SelfPacket extends Packet {
	private final int uniqueID;

	public SelfPacket(AbstractConnection connection, byte[] bytes) {
		super(connection);
		uniqueID = BinaryOperations.intFromBytes(bytes, 1);
	}

	public SelfPacket(AbstractConnection connection, int uniqueID) {
		super(connection);
		this.uniqueID = uniqueID;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.SELF_PACKET;
	}

	/**
	 * @return the uniqueID
	 */
	public int getUniqueID() {
		return uniqueID;
	}

	@Override
	public int sizeOf() {
		return 5;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(uniqueID)) {
			bytes[index++] = b;
		}
		return bytes;
	}

}
