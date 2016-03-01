package com.git.cs309.mmoclient.gui.characterselection;

public final class PlayerCharacter {
	private final int index;
	private int eyeColor;
	private byte gender;
	private int skinColor;
	private int hairColor;
	private int hairStyle;
	
	public PlayerCharacter(final int index) {
		this.index = index;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
}
