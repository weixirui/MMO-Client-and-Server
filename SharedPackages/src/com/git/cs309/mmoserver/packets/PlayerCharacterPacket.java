package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class PlayerCharacterPacket extends Packet {
	public static final byte MALE = 0;
	public static final byte FEMALE = 1;
	public static final int NO_GEAR = 0; // No gear in the slot with this value
	private final byte gender;
	private final int uniqueID;
	private final int x;
	private final int y;
	private final int health;
	private final int maxHealth;
	private final int level;
	private final int headPiece;
	private final int chestPiece;
	private final int leftHand;
	private final int rightHand;
	private final int cape;
	private final int leggings;
	private final int gloves;
	private final int boots;
	private final String name;

	public PlayerCharacterPacket(AbstractConnection source, final byte gender, final int uniqueID, final int x,
			final int y, final int health, final int maxHealth, final int level, final int headPiece,
			final int chestPiece, final int leftHand, final int rightHand, final int cape, final int leggings,
			final int gloves, final int boots, final String name) {
		super(source);
		this.gender = gender;
		this.uniqueID = uniqueID;
		this.x = x;
		this.y = y;
		this.health = health;
		this.maxHealth = maxHealth;
		this.level = level;
		this.headPiece = headPiece;
		this.chestPiece = chestPiece;
		this.leftHand = leftHand;
		this.rightHand = rightHand;
		this.cape = cape;
		this.leggings = leggings;
		this.boots = boots;
		this.gloves = gloves;
		this.name = name;
	}

	public PlayerCharacterPacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		gender = bytes[1];
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 2, 14);
		uniqueID = ints[0];
		x = ints[1];
		y = ints[2];
		health = ints[3];
		maxHealth = ints[4];
		level = ints[5];
		headPiece = ints[6];
		chestPiece = ints[7];
		leftHand = ints[8];
		rightHand = ints[9];
		cape = ints[10];
		leggings = ints[11];
		boots = ints[12];
		gloves = ints[13];
		char[] chars = new char[bytes.length - 58];
		for (int i = 57, j = 0; i < bytes.length; i++, j++) {
			chars[j] = (char) bytes[i];
		}
		name = String.valueOf(chars);
	}

	public int getBoots() {
		return boots;
	}

	public int getCape() {
		return cape;
	}

	public int getChestPiece() {
		return chestPiece;
	}

	public byte getGender() {
		return gender;
	}

	public int getGloves() {
		return gloves;
	}

	public int getHeadPiece() {
		return headPiece;
	}

	public int getHealth() {
		return health;
	}

	public int getLeftHand() {
		return leftHand;
	}

	public int getLeggings() {
		return leggings;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public String getName() {
		return name;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PLAYER_CHARACTER_PACKET;
	}

	public int getRightHand() {
		return rightHand;
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

	@Override
	public int sizeOf() {
		return 58 + name.length();
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = gender;
		for (byte b : BinaryOperations.toBytes(uniqueID, x, y, health, maxHealth, level, headPiece, chestPiece,
				leftHand, rightHand, cape, leggings, boots, gloves)) {
			bytes[index++] = b;
		}
		for (char c : name.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

}
