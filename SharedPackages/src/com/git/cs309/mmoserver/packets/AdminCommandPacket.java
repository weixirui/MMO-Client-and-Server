package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class AdminCommandPacket extends Packet {
	//Unused: 4
	public static final int RESTART_SERVER = 1;
	public static final int RESTART_CONNECTION_MANAGER = 2;
	public static final int RESTART_CYCLE_PROCESS_MANAGER = 3;
	public static final int RESTART_CHARACTER_MANAGER = 4;
	public static final int RESTART_NPC_MANAGER = 5;
	public static final int BAN_USER = 6;
	public static final int IP_BAN_USER = 7;
	public static final int MUTE_USER = 8;
	public static final int IP_MUTE_USER = 9;
	public static final int PROMOTE_USER_ADMIN = 0x10A;
	public static final int PROMOTE_USER_MOD = 0x20A;
	public static final int PROMOTE_USER_PLAYER = 0x30A;

	private final int command;
	private final int additionalField;
	private final int duration; // In hours

	public AdminCommandPacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		command = (bytes[1] << 24) | (bytes[2] << 16) | (bytes[3] << 8) | (bytes[4]);
		additionalField = (bytes[5] << 24) | (bytes[6] << 16) | (bytes[7] << 8) | (bytes[8]);
		duration = (bytes[9] << 24) | (bytes[10] << 16) | (bytes[11] << 8) | (bytes[12]);
	}

	public AdminCommandPacket(AbstractConnection source, final int command, final int additionalField) {
		super(source);
		this.command = command;
		this.additionalField = additionalField;
		duration = 0;
	}

	public AdminCommandPacket(AbstractConnection source, final int command, final int additionalField,
			final int duration) {
		super(source);
		this.command = command;
		this.additionalField = additionalField;
		this.duration = duration;
	}

	public int getAdditionalField() {
		return additionalField;
	}

	public int getCommand() {
		return command;
	}

	public int getDuration() {
		return duration;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ADMIN_COMMAND_PACKET;
	}

	@Override
	public int sizeOf() {
		return 9;
	}

	@Override
	public byte[] toBytes() {
		return new byte[] { getPacketType().getTypeByte(), (byte) (command >> 24), (byte) ((command >> 16) & 0xFF),
				(byte) ((command >> 8) & 0xFF), (byte) (command & 0xFF), (byte) (additionalField >> 24),
				(byte) ((additionalField >> 16) & 0xFF), (byte) ((additionalField >> 8) & 0xFF),
				(byte) (additionalField & 0xFF), (byte) (duration >> 24), (byte) ((duration >> 16) & 0xFF),
				(byte) ((duration >> 8) & 0xFF), (byte) (duration & 0xFF) };
	}

}
