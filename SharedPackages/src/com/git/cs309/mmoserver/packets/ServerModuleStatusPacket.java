package com.git.cs309.mmoserver.packets;

import java.nio.ByteBuffer;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class ServerModuleStatusPacket extends Packet {
	private final float drag;
	private final String name;

	public ServerModuleStatusPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		drag = ByteBuffer.wrap(new byte[] { bytes[1], bytes[2], bytes[3], bytes[4] }).getFloat();
		char[] array = new char[bytes.length - 5];
		for (int i = 5; i < bytes.length; i++) {
			array[i - 5] = (char) bytes[i];
		}
		name = String.valueOf(array);
	}

	public ServerModuleStatusPacket(AbstractConnection source, String name, float drag) {
		super(source);
		this.drag = drag;
		this.name = name;
	}

	public float getDrag() {
		return drag;
	}

	public String getName() {
		return name;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.SERVER_MODULE_STATUS_PACKET;
	}

	@Override
	public int sizeOf() {
		return 5 + name.length();
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : ByteBuffer.allocate(4).putFloat(drag).array()) {
			bytes[index++] = b;
		}
		for (char c : name.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

}
