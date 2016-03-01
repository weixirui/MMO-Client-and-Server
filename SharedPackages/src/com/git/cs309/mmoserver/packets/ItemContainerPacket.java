package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class ItemContainerPacket extends Packet {
	public static final byte INVENTORY = 0;
	public static final byte THEIR_TRADE_ITEMS = 1;
	public static final byte YOUR_TRADE_ITEMS = 2;
	public static final byte BANK = 3;
	public static final byte EQUIPMENT = 4;
	
	public static final int HELMET_SLOT = 0;
	public static final int CHEST_SLOT = 1;
	public static final int LEGS_SLOT = 2;
	public static final int BOOTS_SLOT = 3;
	public static final int GLOVES_SLOT = 4;
	public static final int CAPE_SLOT = 5;
	public static final int RIGHT_HAND = 6;
	public static final int LEFT_HAND = 7;
	public static final int AMMO_SLOT = 8;
	
	private final byte type;
	private final int size;
	private final int[] itemIds;
	private final int[] itemAmounts;
	
	public ItemContainerPacket(AbstractConnection source, byte type, int size, int[] itemIds, int[] itemAmounts) {
		super(source);
		this.type = type;
		this.size = size;
		this.itemIds = itemIds;
		this.itemAmounts = itemAmounts;
	}
	
	public ItemContainerPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		this.type = bytes[1];
		this.size = BinaryOperations.intFromBytes(bytes, 2);
		this.itemIds = BinaryOperations.intArrayFromBytes(bytes, 6, size);
		this.itemAmounts = BinaryOperations.intArrayFromBytes(bytes, 6 + (4 * size));
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = type;
		for (byte b : BinaryOperations.toBytes(itemIds)) {
			bytes[index++] = b;
		}
		for (byte b : BinaryOperations.toBytes(itemAmounts)) {
			bytes[index++] = b;
		}
		return bytes;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ITEM_CONTAINER_PACKET;
	}

	@Override
	public int sizeOf() {
		return 6 + (itemIds.length * 4) + (itemAmounts.length * 4);
	}

	/**
	 * @return the type
	 */
	public byte getType() {
		return type;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the itemIds
	 */
	public int[] getItemIds() {
		return itemIds;
	}

	/**
	 * @return the itemAmounts
	 */
	public int[] getItemAmounts() {
		return itemAmounts;
	}

}
