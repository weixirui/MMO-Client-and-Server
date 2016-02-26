package com.git.cs309.mmoserver.entity.characters.user;

import java.io.Serializable;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.entity.characters.Character;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

public class PlayerCharacter extends Character implements Serializable {
	public static final byte FEMALE = 1;
	public static final byte MALE = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5948438982722793742L;
	private boolean created = false;

	private byte gender = -1; // 0 - Male, 1 - Female

	public PlayerCharacter() {
		super();
	}

	public PlayerCharacter(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.entityID = 0;
		this.name = "Null";
		deleteCharacter();
	}

	public void createCharacter(final String characterName, final byte gender) {
		name = characterName;
		this.gender = gender;
		this.created = true;
	}

	public void deleteCharacter() {
		name = "NULL";
		this.gender = -1;
		this.x = Config.PLAYER_START_X;
		this.y = Config.PLAYER_START_Y;
		this.created = false;
	}

	public byte getGender() {
		return gender;
	}

	@Override
	public int getMaxHealth() {
		return 100; // For now, just set to 100 for testing.
	}

	public boolean isCreated() {
		return created;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

	public void enterGame(final IDTag idTag) {
		assert created;
		setIDTag(idTag);
		Main.getCharacterManager().addCharacter(this);
	}

	public void exitGame() {
		setIDTag(null);
		Main.getCharacterManager().removeCharacter(this);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.PLAYER;
	}

	@Override
	public int getLevel() {
		return 10;// For now, just set to 10 for testing.
	}

}
