package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class ExtensiveObjectPacket extends Packet {
	
	public ExtensiveObjectPacket(AbstractConnection connection, int uniqueID, int staticID, int x, int y, String name) {
		super(connection);
		this.uniqueID = uniqueID;
		this.staticID = staticID;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public ExtensiveObjectPacket(AbstractConnection connection, byte[] bytes) {
		super(connection);
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 1, 4);
		uniqueID = ints[0];
		staticID = ints[1];
		x = ints[2];
		y = ints[3];
		char[] chars = new char[bytes.length - 17];
		for (int i = 16, j = 0; i < bytes.length; i++, j++) {
			chars[j] = (char) bytes[i];
		}
		name = String.valueOf(chars);
	}
	
	private final int x;
	private final int y;
	private final int uniqueID;
	private final int staticID;
	private final String name;
	
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(uniqueID, staticID, x, y)) {
			bytes[index++] = b;
		}
		for (char c : name.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
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
	
	public int getStaticID() {
		return staticID;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.EXTENSIVE_OBJECT_PACKET;
	}

	@Override
	public int sizeOf() {
		return 17 + name.length();
	}

}
