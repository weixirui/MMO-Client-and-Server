package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class MovePacket extends Packet {
	private final int x, y;

	public MovePacket(AbstractConnection connection, byte[] bytes) {
		super(connection);
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 1);
		x = ints[0];
		y = ints[1];
	}

	public MovePacket(AbstractConnection connection, int x, int y) {
		super(connection);
		this.x = x;
		this.y = y;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.MOVE_PACKET;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	@Override
	public int sizeOf() {
		return 9;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(x, y)) {
			bytes[index++] = b;
		}
		return bytes;
	}

}
