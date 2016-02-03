package com.git.cs309.mmoclient.gui;

import java.awt.Color;

import com.git.cs309.mmoserver.packets.MessagePacket;

public enum MessageGroup {
	ALL((byte) 100, Color.WHITE), PRIVATE(MessagePacket.PRIVATE_CHAT, Color.PINK), PARTY(MessagePacket.PARTY_CHAT, Color.BLUE), GLOBAL(MessagePacket.GLOBAL_CHAT, Color.ORANGE), LOCAL(
			MessagePacket.LOCAL_CHAT, Color.WHITE);

	public static MessageGroup getGroupForByte(final byte groupByte) {
		switch (groupByte) {
		case MessagePacket.GLOBAL_CHAT:
			return GLOBAL;
		case MessagePacket.LOCAL_CHAT:
			return LOCAL;
		case MessagePacket.PARTY_CHAT:
			return PARTY;
		case MessagePacket.PRIVATE_CHAT:
			return PRIVATE;
		default:
			return LOCAL;
		}
	}

	private final byte groupByte;
	private final Color color;

	private MessageGroup(final byte groupByte, final Color color) {
		this.groupByte = groupByte;
		this.color = color;
	}

	public byte getGroupByte() {
		return groupByte;
	}
	
	public Color getColor() {
		return color;
	}
}
