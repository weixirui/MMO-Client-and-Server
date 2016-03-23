package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class GroundItemsPacket extends Packet {
	
	private final int x, y;
	private final int[] itemIds;
	
	public GroundItemsPacket(AbstractConnection connection, int x, int y, int ... itemIds) {
		super (connection);
		this.x = x;
		this.y = y;
		this.itemIds = itemIds;
	}
	
	public GroundItemsPacket(AbstractConnection connection, byte[] bytes) {
		super (connection);
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 1, 2);
		x = ints[0];
		y = ints[1];
		itemIds = BinaryOperations.intArrayFromBytes(bytes, 9);
	}
	
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(x, y)) {
			bytes[index++] = b;
		}
		for (byte b : BinaryOperations.toBytes(itemIds)) {
			bytes[index++] = b;
		}
		return bytes;
	}
	
	public int[] getItemIds() {
		return itemIds;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.GROUND_ITEM_PACKET;
	}

	@Override
	public int sizeOf() {
		return 9 + (itemIds.length * 4);
	}

}
