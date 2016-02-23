package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class AdminCommandPacket extends Packet {
	public static final int RESTART_SERVER = 1;
	public static final int RESTART_CONNECTION_MANAGER = 2;
	public static final int RESTART_CYCLE_PROCESS_MANAGER = 3;
	public static final int RESTART_CHARACTER_MANAGER = 4;
	public static final int RESTART_NPC_MANAGER = 5;
	
	private final int command;
	
	public AdminCommandPacket(AbstractConnection source, final int command) {
		super(source);
		this.command = command;
	}
	
	public AdminCommandPacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		command = (bytes[1] << 24) | (bytes[2] << 16) | (bytes[3] << 8) | (bytes[4]);
	}
	
	public int getCommand() {
		return command;
	}

	@Override
	public byte[] toBytes() {
		return new byte[] {getPacketType().getTypeByte(), (byte) (command >> 24), (byte) ((command >> 16) & 0xFF), (byte) ((command >> 8) & 0xFF), (byte) (command & 0xFF)};
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ADMIN_COMMAND_PACKET;
	}

}
