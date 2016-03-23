package com.git.cs309.mmoserver.entity.characters.user;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.cycle.CycleProcess;
import com.git.cs309.mmoserver.cycle.CycleProcessManager;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.entity.characters.Character;
import com.git.cs309.mmoserver.entity.characters.CharacterManager;
import com.git.cs309.mmoserver.items.ItemContainer;
import com.git.cs309.mmoserver.items.ItemStack;
import com.git.cs309.mmoserver.map.Map;
import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.packets.ExtensivePlayerCharacterPacket;
import com.git.cs309.mmoserver.packets.Packet;

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
	private boolean created = false;
	private ItemContainer inventory = new ItemContainer(40);
	private Equipment equipment = new Equipment();

	private byte gender = -1; // 0 - Male, 1 - Female
	private int eyeColor = 0;
	private int skinColor = 0;
	private int hairColor = 0;
	private int hairStyle = 0;

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

	/**
	 * All this method does is make it so this character object is playable. It
	 * doesn't actually create anything.
	 * 
	 * @param characterName
	 *            name of the new character
	 * @param gender
	 *            gender of the new character
	 */
	public void createCharacter(final String characterName, final byte gender, int eyeColor, int skinColor,
			int hairColor, int hairStyle) {
		name = characterName;
		this.gender = gender;
		this.created = true;
		this.eyeColor = eyeColor;
		this.skinColor = skinColor;
		this.hairColor = hairColor;
		this.hairStyle = hairStyle;
		this.health = 10;
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
	public void enterGame(final User user) {
		assert created;
		setIDTag(user.getIdTag());
		CharacterManager.getInstance().addCharacter(this);
		user.getConnection().addOutgoingPacket(getExtensivePacket());
	}

	/**
	 * Nulls the ID Tag and removes character from manages.
	 */
	public void exitGame() {
		CharacterManager.getInstance().removeCharacter(this);
		setIDTag(null);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.PLAYER;
	}

	@Override
	public Packet getExtensivePacket() {
		ItemStack currEquipment = null;
		currEquipment = equipment.getEquipment(Equipment.HELMET_SLOT);
		int helmetId = currEquipment == null ? -1 : currEquipment.getId();
		currEquipment = equipment.getEquipment(Equipment.CHEST_SLOT);
		int chestId = currEquipment == null ? -1 : currEquipment.getId();
		currEquipment = equipment.getEquipment(Equipment.LEGS_SLOT);
		int legsId = currEquipment == null ? -1 : currEquipment.getId();
		currEquipment = equipment.getEquipment(Equipment.GLOVES_SLOT);
		int glovesId = currEquipment == null ? -1 : currEquipment.getId();
		currEquipment = equipment.getEquipment(Equipment.BOOTS_SLOT);
		int bootsId = currEquipment == null ? -1 : currEquipment.getId();
		currEquipment = equipment.getEquipment(Equipment.CAPE_SLOT);
		int capeId = currEquipment == null ? -1 : currEquipment.getId();
		currEquipment = equipment.getEquipment(Equipment.RIGHT_HAND);
		int rightId = currEquipment == null ? -1 : currEquipment.getId();
		currEquipment = equipment.getEquipment(Equipment.LEFT_HAND);
		int leftId = currEquipment == null ? -1 : currEquipment.getId();
		return new ExtensivePlayerCharacterPacket(null, gender, getUniqueID(), getX(), getY(), getHealth(),
				getMaxHealth(), getLevel(), helmetId, chestId, leftId, rightId, capeId, legsId, glovesId, bootsId,
				eyeColor, skinColor, hairColor, hairStyle, name);
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
	protected void characterProcess() {

	}

	@Override
	protected boolean canWalk() {
		return true;
	}

	@Override
	protected void onDeath() {
		Map map = MapHandler.getInstance().getMapContainingEntity(PlayerCharacter.this);
		for (ItemStack stack : inventory.removeAllAsList()) {
			map.putItemStack(getX(), getY(), stack);
		}
		CycleProcessManager.getInstance().addProcess(new CycleProcess() {
			final long startTick = Main.getTickCount();

			@Override
			public void end() {
				isDead = false;
				health = getMaxHealth();
				setPosition(Config.GLOBAL_INSTANCE, Config.PLAYER_START_X, Config.PLAYER_START_Y,
						Config.PLAYER_START_Z);
			}

			@Override
			public boolean finished() {
				return Main.getTickCount() - startTick >= Config.TICKS_BEFORE_PLAYER_RESPAWN;
			}

			@Override
			public void process() {

			}

		});
	}

}
