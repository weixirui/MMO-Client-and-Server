package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class CharacterSelectedPacket extends Packet {
	private final int index;
	
	public CharacterSelectedPacket(AbstractConnection source, int index) {
		super(source);
		this.index = index;
	}
	
	public CharacterSelectedPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		index = bytes[1];
	}

	@Override
	public byte[] toBytes() {
		return new byte[] {getPacketType().getTypeByte(), (byte) (index & 0xFF)};
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.CHARACTER_SELECTED_PACKET;
	}

}
