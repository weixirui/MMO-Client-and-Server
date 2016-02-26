package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class EntityUpdatePacket extends Packet {
	public static final byte MOVED = 1;
	public static final byte REMOVED = 3;
	public static final byte WALKING = 2;
	public static final byte STATIC = 4;
	public static final byte SKILL_ACTION_1 = 5;
	public static final byte SKILL_ACTION_2 = 6;
	public static final byte SKILL_ACTION_3 = 7;
	public static final byte SKILL_ACTION_4 = 8;
	public static final byte SKILL_ACTION_5 = 9;
	public static final byte HEALING_SKILL = 10;
	public static final byte UTILITY_SKILL_1 = 11;
	public static final byte UTILITY_SKILL_2 = 12;
	public static final byte UTILITY_SKILL_3 = 13;
	public static final byte ELITE_SKILL = 14;
	private final int uniqueID;
	private final int x;
	private final int y;
	private final byte args;
	
	public EntityUpdatePacket(AbstractConnection source, final byte args, final int uniqueID, final int x, final int y) {
		super(source);
		this.args = args;
		this.uniqueID = uniqueID;
		this.x = x;
		this.y = y;
	}
	
	public EntityUpdatePacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		this.args = bytes[1];
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
	
	public byte getArgs() {
		return args;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = args;
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
