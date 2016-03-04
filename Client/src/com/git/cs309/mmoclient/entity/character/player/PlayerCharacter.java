package com.git.cs309.mmoclient.entity.character.player;

import com.git.cs309.mmoclient.entity.EntityType;
import com.git.cs309.mmoclient.entity.character.Character;
import com.git.cs309.mmoclient.graphics.Sprite;
import com.git.cs309.mmoclient.graphics.SpriteDatabase;
import com.git.cs309.mmoserver.packets.ExtensivePlayerCharacterPacket;

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
public class PlayerCharacter extends Character {
	//Female state
	public static final byte FEMALE = 1;
	//Male state
	public static final byte MALE = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5948438982722793742L;

	private byte gender = -1; // 0 - Male, 1 - Female
	private int eyeColor = 0;
	private int skinColor = 0;
	private int hairColor = 0;
	private int hairStyle = 0;

	public PlayerCharacter(ExtensivePlayerCharacterPacket packet) {
		super(packet.getX(), packet.getY(), packet.getUniqueID(), 0, packet.getName());
		this.maxHealth = packet.getMaxHealth();
		this.health = packet.getHealth();
		this.gender = packet.getGender();
		this.eyeColor = packet.getEyeColor();
		this.skinColor = packet.getSkinColor();
		this.hairColor = packet.getHairColor();
		this.hairStyle = packet.getHairStyle();
		this.level = packet.getLevel();
	}
	
	@Override
	public Sprite getSprite() {
		return SpriteDatabase.getInstance().getSprite("tempplayer");
	}

	public int getEyeColor() {
		return eyeColor;
	}

	public int getSkinColor() {
		return skinColor;
	}

	public int getHairColor() {
		return hairColor;
	}

	public int getHairStyle() {
		return hairStyle;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.PLAYER;
	}

	public byte getGender() {
		return gender;
	}

}
