package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class UserStatusPacket extends Packet {
	public static final byte LOGGED_IN = 0;
	public static final byte IN_GAME = 1;
	public static final byte DISCONNECTED = 2;

	private final byte status;
	private final int userID;
	private final String username;
	private final String permissions;

	public UserStatusPacket(AbstractConnection destination, byte[] bytes) {
		super(destination);
		this.status = bytes[1];
		this.userID = (bytes[2] << 24) | (bytes[3] << 16) | (bytes[4] << 8) | bytes[5];
		int index = 6;
		int buffLen = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		String buff = "";
		for (int i = 0; i < buffLen; i++) {
			buff += (char) bytes[index++];
		}
		username = buff;
		buffLen = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		buff = "";
		for (int i = 0; i < buffLen; i++) {
			buff += (char) bytes[index++];
		}
		permissions = buff;
	}

	public UserStatusPacket(AbstractConnection destination, final int userID, final String username,
			final String permissions, final byte status) {
		super(destination);
		this.status = status;
		this.userID = userID;
		this.username = username;
		this.permissions = permissions;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.USER_STATUS_PACKET;
	}

	public String getPermissions() {
		return permissions;
	}

	public byte getStatus() {
		return status;
	}

	public int getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int sizeOf() {
		return 14 + username.length() + permissions.length();
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = (byte) (userID >> 24);
		bytes[index++] = (byte) ((userID >> 16) & 0xFF);
		bytes[index++] = (byte) ((userID >> 8) & 0xFF);
		bytes[index++] = (byte) (userID & 0xFF);
		bytes[index++] = (byte) (username.length() >> 24);
		bytes[index++] = (byte) ((username.length() >> 16) & 0xFF);
		bytes[index++] = (byte) ((username.length() >> 8) & 0xFF);
		bytes[index++] = (byte) (username.length() & 0xFF);
		for (char c : username.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		bytes[index++] = (byte) (permissions.length() >> 24);
		bytes[index++] = (byte) ((permissions.length() >> 16) & 0xFF);
		bytes[index++] = (byte) ((permissions.length() >> 8) & 0xFF);
		bytes[index++] = (byte) (permissions.length() & 0xFF);
		for (char c : permissions.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

}
