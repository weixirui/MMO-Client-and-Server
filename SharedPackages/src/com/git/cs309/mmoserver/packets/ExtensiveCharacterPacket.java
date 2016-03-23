package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class ExtensiveCharacterPacket extends Packet {
	private final int x;
	private final int y;
	private final int uniqueID;
	private final int characterID;
	private final int health;
	private final int maxHealth;
	private final int level;
	private final String characterName;

	public ExtensiveCharacterPacket(AbstractConnection connection, final byte[] bytes) {
		super(connection);
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 1, 7);
		uniqueID = ints[0];
		characterID = ints[1];
		x = ints[2];
		y = ints[3];
		health = ints[4];
		maxHealth = ints[5];
		level = ints[6];
		char[] chars = new char[bytes.length - 29];
		for (int i = 29, j = 0; i < bytes.length; i++, j++) {
			chars[j] = (char) bytes[i];
		}
		characterName = String.valueOf(chars);
	}

	public ExtensiveCharacterPacket(AbstractConnection connection, final int uniqueID, final int characterID,
			final int x, final int y, final int health, final int maxHealth, final int level,
			final String characterName) {
		super(connection);
		this.x = x;
		this.y = y;
		this.uniqueID = uniqueID;
		this.characterID = characterID;
		this.health = health;
		this.maxHealth = maxHealth;
		this.level = level;
		this.characterName = characterName;
	}

	public int getCharacterID() {
		return characterID;
	}

	public String getCharacterName() {
		return characterName;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.EXTENSIVE_CHARACTER_PACKET;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getLevel() {
		return level;
	}

	@Override
	public int sizeOf() {
		return 29 + characterName.length();
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(uniqueID, characterID, x, y, health, maxHealth, level)) {
			bytes[index++] = b;
		}
		for (char c : characterName.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

}
