package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class CharacterStatusPacket extends Packet {
	public static final int PLAYER_CHARACTER_ID = 0; // any character ID not 0 is an NPC
	private final int health;
	private final int maxHealth;
	private final int level;
	private final int mana;
	private final int maxMana;
	private final int experience;
	private final int maxExperience;
	private final int characterID;
	private final int uniqueID;

	public CharacterStatusPacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		int[] values = BinaryOperations.intArrayFromBytes(bytes, 1);
		health = values[0];
		maxHealth = values[1];
		level = values[2];
		mana = values[3];
		maxMana = values[4];
		experience = values[5];
		maxExperience = values[6];
		characterID = values[7];
		uniqueID = values[8];
	}

	public CharacterStatusPacket(AbstractConnection source, final int health, final int maxHealth, final int level,
			final int mana, final int maxMana, final int experience, final int maxExperience, final int characterID,
			final int uniqueID) {
		super(source);
		this.health = health;
		this.maxHealth = maxHealth;
		this.level = level;
		this.mana = mana;
		this.maxMana = maxMana;
		this.experience = experience;
		this.maxExperience = maxExperience;
		this.characterID = characterID;
		this.uniqueID = uniqueID;
	}

	public int getCharacterID() {
		return characterID;
	}

	public int getExperience() {
		return experience;
	}

	public int getHealth() {
		return health;
	}

	public int getLevel() {
		return level;
	}

	public int getMana() {
		return mana;
	}

	public int getMaxExperience() {
		return maxExperience;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getMaxMana() {
		return maxMana;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.CHARACTER_STATUS_PACKET;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	@Override
	public int sizeOf() {
		return 37;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(health, maxHealth, level, mana, maxMana, experience, maxExperience,
				characterID, uniqueID)) {
			bytes[index++] = b;
		}
		return bytes;
	}
}
