package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class NewCharacterDataPacket extends Packet {
	public static final byte MALE = ExtensivePlayerCharacterPacket.MALE;
	public static final byte FEMALE = ExtensivePlayerCharacterPacket.FEMALE;
	
	private final int eyeColor;
	private final int skinColor;
	private final int hairColor;
	private final int hairStyle;
	private final byte gender;
	private final String name;

	public NewCharacterDataPacket(AbstractConnection source, int eyeColor, int skinColor, int hairColor, int hairStyle, byte gender, String name) {
		super(source);
		this.eyeColor = eyeColor;
		this.skinColor = skinColor;
		this.hairColor = hairColor;
		this.hairStyle = hairStyle;
		this.gender = gender;
		this.name = name;
	}
	
	public NewCharacterDataPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		gender = bytes[1];
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 2, 4);
		int index = 0;
		eyeColor = ints[index++];
		skinColor = ints[index++];
		hairColor = ints[index++];
		hairStyle = ints[index++];
		char[] chars = new char[bytes.length - 18];
		for (int i = 0, j = 18; j < bytes.length; j++, i++) {
			chars[i] = (char) bytes[j];
		}
		name = String.valueOf(chars);
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = gender;
		for (byte b : BinaryOperations.toBytes(eyeColor, skinColor, hairColor, hairStyle)) {
			bytes[index++] = b;
		}
		for (char c : name.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.NEW_CHARACTER_DATA_PACKET;
	}

	@Override
	public int sizeOf() {
		return 18 + name.length();
	}

	/**
	 * @return the gender
	 */
	public byte getGender() {
		return gender;
	}

	/**
	 * @return the hairStyle
	 */
	public int getHairStyle() {
		return hairStyle;
	}

	/**
	 * @return the hairColor
	 */
	public int getHairColor() {
		return hairColor;
	}

	/**
	 * @return the skinColor
	 */
	public int getSkinColor() {
		return skinColor;
	}

	/**
	 * @return the eyeColor
	 */
	public int getEyeColor() {
		return eyeColor;
	}
	
	public String getName() {
		return name;
	}

}
