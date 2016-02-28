package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class CharacterStatusPacket extends Packet {
	private final int uniqueID;
	private final int mana;
	private final int maxMana;
	private final int health;
	private final int maxHealth;
	private final int experience;
	private final int maxExperience;

	public CharacterStatusPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 1);
		this.uniqueID = ints[0];
		this.mana = ints[1];
		this.maxMana = ints[2];
		this.health = ints[3];
		this.maxHealth = ints[4];
		this.experience = ints[5];
		this.maxExperience = ints[6];
	}

	public CharacterStatusPacket(AbstractConnection source, int uniqueID, int mana, int maxMana, int health,
			int maxHealth, int experience, int maxExperience) {
		super(source);
		this.uniqueID = uniqueID;
		this.mana = mana;
		this.maxMana = maxMana;
		this.health = health;
		this.maxHealth = maxHealth;
		this.experience = experience;
		this.maxExperience = maxExperience;
	}

	public int getExperience() {
		return experience;
	}

	public int getHealth() {
		return health;
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
		return 29;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(uniqueID, mana, maxMana, health, maxHealth, experience, maxExperience)) {
			bytes[index++] = b;
		}
		return bytes;
	}

}
