package com.git.cs309.mmoserver.entity.characters.user;

import java.io.Serializable;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.entity.characters.Character;
import com.git.cs309.mmoserver.entity.characters.CharacterManager;
import com.git.cs309.mmoserver.items.ItemContainer;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

/**
 * 
 * @author Group 21
 *
 *         <p>
 *         Defines the PlayerCharacter, which each user will use to interact
 *         with the game. PlayerCharacters will always have the static ID 0, and
 *         their appearance will depend on their gender and their gear.
 *         </p>
 */
public class PlayerCharacter extends Character implements Serializable {
	//Female state
	public static final byte FEMALE = 1;
	//Male state
	public static final byte MALE = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5948438982722793742L;
	private boolean created = false;
	private ItemContainer inventory = new ItemContainer(40);

	private byte gender = -1; // 0 - Male, 1 - Female

	public PlayerCharacter() {
		super(); //Ensure calls constructor
	}

	public PlayerCharacter(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.entityID = 0;
		this.name = "Null";
		deleteCharacter();
	}

	/**
	 * All this method does is make it so this character object is playable. It
	 * doesn't actually create anything.
	 * 
	 * @param characterName
	 *            name of the new character
	 * @param gender
	 *            gender of the new character
	 */
	public void createCharacter(final String characterName, final byte gender) {
		name = characterName;
		this.gender = gender;
		this.created = true;
	}

	/**
	 * Doesn't actually delete the character, just makes it unplayable until you
	 * recreate it.
	 */
	public void deleteCharacter() {
		name = "NULL";
		this.gender = -1;
		this.x = Config.PLAYER_START_X;
		this.y = Config.PLAYER_START_Y;
		this.created = false;
		inventory.deleteAll();
	}
	
	public ItemContainer getInventory() {
		return inventory;
	}

	/**
	 * Adds this character to the handlers, and gives it the same ID tag as the
	 * User controlling it.
	 * 
	 * @param idTag
	 *            the User controlling this characters ID tag, ideally.
	 */
	public void enterGame(final IDTag idTag) {
		assert created;
		setIDTag(idTag);
		CharacterManager.getInstance().addCharacter(this);
	}

	/**
	 * Nulls the ID Tag and removes character from manages.
	 */
	public void exitGame() {
		setIDTag(null);
		CharacterManager.getInstance().removeCharacter(this);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.PLAYER;
	}

	@Override
	public Packet getExtensivePacket() {
		// TODO Auto-generated method stub
		return null;
	}

	public byte getGender() {
		return gender;
	}

	@Override
	public int getLevel() {
		return 10;// For now, just set to 10 for testing.
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

}
