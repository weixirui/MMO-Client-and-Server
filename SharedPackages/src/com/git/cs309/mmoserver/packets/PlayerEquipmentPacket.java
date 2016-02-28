package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class PlayerEquipmentPacket extends Packet {

	private final int uniqueID;
	private final int headPiece;
	private final int chestPiece;
	private final int rightHand;
	private final int leftHand;
	private final int leggings;
	private final int boots;
	private final int gloves;
	private final int cape;

	public PlayerEquipmentPacket(AbstractConnection source, byte[] bytes) {
		super(source);
		int[] ints = BinaryOperations.intArrayFromBytes(bytes, 1);
		int index = 0;
		uniqueID = ints[index++];
		headPiece = ints[index++];
		chestPiece = ints[index++];
		rightHand = ints[index++];
		leftHand = ints[index++];
		leggings = ints[index++];
		boots = ints[index++];
		gloves = ints[index++];
		cape = ints[index++];
	}

	public PlayerEquipmentPacket(AbstractConnection source, int uniqueID, int headPiece, int chestPiece, int rightHand,
			int leftHand, int leggings, int boots, int gloves, int cape) {
		super(source);
		this.uniqueID = uniqueID;
		this.headPiece = headPiece;
		this.chestPiece = chestPiece;
		this.rightHand = rightHand;
		this.leftHand = leftHand;
		this.leggings = leggings;
		this.boots = boots;
		this.gloves = gloves;
		this.cape = cape;
	}

	/**
	 * @return the boots
	 */
	public int getBoots() {
		return boots;
	}

	/**
	 * @return the cape
	 */
	public int getCape() {
		return cape;
	}

	/**
	 * @return the chestPiece
	 */
	public int getChestPiece() {
		return chestPiece;
	}

	/**
	 * @return the gloves
	 */
	public int getGloves() {
		return gloves;
	}

	/**
	 * @return the headPiece
	 */
	public int getHeadPiece() {
		return headPiece;
	}

	/**
	 * @return the leftHand
	 */
	public int getLeftHand() {
		return leftHand;
	}

	/**
	 * @return the leggings
	 */
	public int getLeggings() {
		return leggings;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PLAYER_EQUIPMENT_PACKET;
	}

	/**
	 * @return the rightHand
	 */
	public int getRightHand() {
		return rightHand;
	}

	/**
	 * @return the uniqueID
	 */
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
		for (byte b : BinaryOperations.toBytes(uniqueID, headPiece, chestPiece, rightHand, leftHand, leggings, boots,
				gloves, cape)) {
			bytes[index++] = b;
		}
		return bytes;
	}

}
