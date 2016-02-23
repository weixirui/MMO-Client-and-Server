package com.git.cs309.mmoserver.characters.user;

import java.io.Serializable;

import com.git.cs309.mmoserver.characters.Character;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

public class PlayerCharacter extends Character implements Serializable {
	private boolean created = false;
	private String characterName = "NULL";
	public static final byte MALE = 0;
	public static final byte FEMALE = 1;
	private byte gender = -1; // 0 - Male, 1 - Female

	public PlayerCharacter(int x, int y) {
		super(x, y, null);
		deleteCharacter();
	}
	
	public PlayerCharacter() {
		
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

	public void setIDTag(IDTag idTag) {
		this.idTag = idTag;
	}

	public boolean isCreated() {
		return created;
	}

	public byte getGender() {
		return gender;
	}

	public String getCharacterName() {
		return characterName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5948438982722793742L;

	@Override
	public void applyDamage(int damageAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void applyRegen(int regenAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

}
