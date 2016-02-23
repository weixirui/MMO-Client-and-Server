package com.git.cs309.mmoserver.characters.user;

import java.io.Serializable;

import com.git.cs309.mmoserver.characters.Character;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

public class PlayerCharacter extends Character implements Serializable {
	public static final byte FEMALE = 1;
	public static final byte MALE = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5948438982722793742L;
	private String characterName = "NULL";
	private boolean created = false;

	private byte gender = -1; // 0 - Male, 1 - Female

	public PlayerCharacter() {

	}

	public PlayerCharacter(int x, int y) {
		super(x, y, null);
		deleteCharacter();
	}

	@Override
	public void applyDamage(int damageAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void applyRegen(int regenAmount) {
		// TODO Auto-generated method stub

	}

	public void createCharacter(final String characterName, final byte gender) {
		this.characterName = characterName;
		this.gender = gender;
		this.created = true;
	}

	public void deleteCharacter() {
		this.characterName = "NULL";
		this.gender = -1;
		this.created = false;
	}

	public String getCharacterName() {
		return characterName;
	}

	public byte getGender() {
		return gender;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isCreated() {
		return created;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIDTag(IDTag idTag) {
		this.idTag = idTag;
	}

}
