package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class ErrorPacket extends Packet {

	public static final int GENERAL_ERROR = 1;
	public static final int LOGIN_ERROR = 2;
	public static final int PERMISSION_ERROR = 3;
	public static final int NAME_TOO_LONG_ERROR = 4;
	private final int errorCode;
	private final String errorMessage;

	public ErrorPacket(final AbstractConnection destination, final int errorCode, final String errorMessage) {
		super(destination);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ErrorPacket(final byte[] bytes, final AbstractConnection source) {
		super(source);
		int index = 1;
		errorCode = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		String buffer = "";
		for (; index < bytes.length; index++) {
			buffer += (char) bytes[index];
		}
		errorMessage = buffer;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ERROR_PACKET;
	}

	@Override
	public int sizeOf() {
		return 5 + errorMessage.length();
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()]; // 4 bytes error code, 1 byte packet type, rest errorMessage
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = (byte) (errorCode >> 24);
		bytes[index++] = (byte) ((errorCode >> 16) & 0xFF);
		bytes[index++] = (byte) ((errorCode >> 8) & 0xFF);
		bytes[index++] = (byte) (errorCode & 0xFF);
		for (char c : errorMessage.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

}
