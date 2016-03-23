package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class InterfaceClickPacket extends Packet {
	public static final int CHARACTER_1_SLOT = 0;
	public static final int CHARACTER_2_SLOT = 1;
	public static final int CHARACTER_3_SLOT = 2;
	public static final int CHARACTER_4_SLOT = 3;
	public static final int CHARACTER_5_SLOT = 4;
	//public static final int CONFIRM_CHARACTER_CREATION = 5;
	public static final int OKAY_BUTTON = 6;
	public static final int CANCEL_BUTTON = 7;
	public static final int SKILL_ACTION_1 = 8;
	public static final int SKILL_ACTION_2 = 9;
	public static final int SKILL_ACTION_3 = 10;
	public static final int SKILL_ACTION_4 = 11;
	public static final int SKILL_ACTION_5 = 12;
	public static final int HEALING_SKILL = 13;
	public static final int UTILITY_SKILL_1 = 14;
	public static final int UTILITY_SKILL_2 = 15;
	public static final int UTILITY_SKILL_3 = 16;
	public static final int ELITE_SKILL = 17;
	//public static final int CHARACTER_STATS_OPEN = 18;
	//public static final int PARTY_MENU_OPEN = 19;
	//public static final int FRIENDS_LIST_OPEN = 20;
	//public static final int IGNORE_LIST_OPEN = 21;
	public static final int ITEM_CLICK = 22;
	public static final int MENU_CLICK = 23;
	public static final int WEAPON_SWAP = 24;
	private final int interfaceID;
	private final int clickArgs1;
	private final int clickArgs2;
	private final int clickArgs3;

	public InterfaceClickPacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		int index = 1;
		interfaceID = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		clickArgs1 = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		clickArgs2 = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		clickArgs3 = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
	}

	public InterfaceClickPacket(AbstractConnection source, final int interfaceID, final int clickArgs1,
			final int clickArgs2, final int clickArgs3) {
		super(source);
		this.interfaceID = interfaceID;
		this.clickArgs1 = clickArgs1;
		this.clickArgs2 = clickArgs2;
		this.clickArgs3 = clickArgs3;
	}

	public int getClickArgs1() {
		return clickArgs1;
	}

	public int getClickArgs2() {
		return clickArgs2;
	}

	public int getClickArgs3() {
		return clickArgs3;
	}

	public int getInterfaceID() {
		return interfaceID;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.INTERFACE_CLICK_PACKET;
	}

	@Override
	public int sizeOf() {
		return 17;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[17];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(interfaceID, clickArgs1, clickArgs2, clickArgs3)) {
			bytes[index++] = b;
		}
		return bytes;
	}

}
