package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class CharacterSelectionDataPacket extends Packet {
	private final int index;
	private final int eyeColor;
	private final int skinColor;
	private final int hairColor;
	private final int hairStyle;
	private final String name;
	private final byte gender;
	
	public CharacterSelectionDataPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		this.gender = bytes[1];
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 2, 5);
		int index = 0;
		this.index = ints[index++];
		eyeColor = ints[index++];
		skinColor = ints[index++];
		hairColor = ints[index++];
		hairStyle = ints[index++];
		char[] chars = new char[bytes.length - 22];
		for (int i = 0, j = 22; j < bytes.length; j++, i++) {
			chars[i] = (char) bytes[j];
		}
		name = String.valueOf(chars);
	}

	public CharacterSelectionDataPacket(AbstractConnection source, int index, int eyeColor, int skinColor, int hairColor, int hairStyle, byte gender, String name) {
		super(source);
		this.index = index;
		this.eyeColor = eyeColor;
		this.skinColor = skinColor;
		this.hairColor = hairColor;
		this.hairStyle = hairStyle;
		this.gender = gender;
		this.name = name;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = gender;
		for (byte b : BinaryOperations.toBytes(this.index, eyeColor, skinColor, hairColor, hairStyle)) {
			bytes[index++] = b;
		}
		for (char c : name.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.CHARACTER_SELECTION_DATA_PACKET;
	}

	@Override
	public int sizeOf() {
		return 22 + name.length();
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the eyeColor
	 */
	public int getEyeColor() {
		return eyeColor;
	}

	/**
	 * @return the skinColor
	 */
	public int getSkinColor() {
		return skinColor;
	}

	/**
	 * @return the hairColor
	 */
	public int getHairColor() {
		return hairColor;
	}

	/**
	 * @return the hairStyle
	 */
	public int getHairStyle() {
		return hairStyle;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the gender
	 */
	public byte getGender() {
		return gender;
	}

}
