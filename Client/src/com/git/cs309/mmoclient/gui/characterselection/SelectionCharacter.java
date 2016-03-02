package com.git.cs309.mmoclient.gui.characterselection;

import com.git.cs309.mmoserver.packets.CharacterSelectionDataPacket;

public final class SelectionCharacter {
	private final int index;
	private int eyeColor;
	private byte gender;
	private int skinColor;
	private int hairColor;
	private int hairStyle;
	private boolean hasCharacter = false;
	private String name;
	
	public SelectionCharacter(final int index) {
		this.index = index;
	}
	
	public void setCharacter(CharacterSelectionDataPacket packet) {
		assert packet.getIndex() == index;
		setEyeColor(packet.getEyeColor());
		setGender(packet.getGender());
		setSkinColor(packet.getSkinColor());
		setHairColor(packet.getHairColor());
		setHairStyle(packet.getHairStyle());
		setName(packet.getName());
		setHasCharacter(true);
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the hasCharacter
	 */
	public boolean hasCharacter() {
		return hasCharacter;
	}

	/**
	 * @param hasCharacter the hasCharacter to set
	 */
	public void setHasCharacter(boolean hasCharacter) {
		this.hasCharacter = hasCharacter;
	}

	/**
	 * @return the hairStyle
	 */
	public int getHairStyle() {
		return hairStyle;
	}

	/**
	 * @param hairStyle the hairStyle to set
	 */
	public void setHairStyle(int hairStyle) {
		this.hairStyle = hairStyle;
	}

	/**
	 * @return the hairColor
	 */
	public int getHairColor() {
		return hairColor;
	}

	/**
	 * @param hairColor the hairColor to set
	 */
	public void setHairColor(int hairColor) {
		this.hairColor = hairColor;
	}

	/**
	 * @return the skinColor
	 */
	public int getSkinColor() {
		return skinColor;
	}

	/**
	 * @param skinColor the skinColor to set
	 */
	public void setSkinColor(int skinColor) {
		this.skinColor = skinColor;
	}

	/**
	 * @return the gender
	 */
	public byte getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(byte gender) {
		this.gender = gender;
	}

	/**
	 * @return the eyeColor
	 */
	public int getEyeColor() {
		return eyeColor;
	}

	/**
	 * @param eyeColor the eyeColor to set
	 */
	public void setEyeColor(int eyeColor) {
		this.eyeColor = eyeColor;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
