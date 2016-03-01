package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class NewMapPacket extends Packet {

	private String mapName;

	public NewMapPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		char[] chars = new char[bytes.length - 1];
		for (int i = 0, j = 1; j < bytes.length; i++, j++) {
			chars[i] = (char) bytes[j];
		}
		mapName = String.valueOf(chars);
	}

	public NewMapPacket(AbstractConnection source, String mapName) {
		super(source);
		this.mapName = mapName;
	}

	public String getMapName() {
		return mapName;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_MAP_PACKET;
	}

	@Override
	public int sizeOf() {
		return mapName.length() + 1;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (char c : mapName.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

}
