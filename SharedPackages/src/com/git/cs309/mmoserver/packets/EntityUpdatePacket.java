package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class EntityUpdatePacket extends Packet {
	public static final byte MOVED = 1;
	public static final byte REMOVED = 3;
	private final int uniqueID;
	private final int x;
	private final int y;
	private final byte reason;
	
	public EntityUpdatePacket(AbstractConnection source, final byte reason, final int uniqueID, final int x, final int y) {
		super(source);
		this.reason = reason;
		this.uniqueID = uniqueID;
		this.x = x;
		this.y = y;
	}
	
	public EntityUpdatePacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		this.reason = bytes[1];
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 2);
		uniqueID = ints[0];
		x = ints[1];
		y = ints[2];
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getUniqueID() {
		return uniqueID;
	}
	
	public byte getReason() {
		return reason;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = reason;
		for (byte b : BinaryOperations.toBytes(uniqueID, x, y)) {
			bytes[index++] = b;
		}
		return bytes;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_UPDATE_PACKET;
	}

	@Override
	public int sizeOf() {
		return 14;
	}
}
