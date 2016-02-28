package com.git.cs309.mmoserver.entity.characters.npc;

import com.git.cs309.mmoserver.entity.EntityType;
/**
 *
 * @author Group 21
 * 
 */
import com.git.cs309.mmoserver.entity.characters.Character;
import com.git.cs309.mmoserver.packets.ExtensiveCharacterPacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.util.ClosedIDSystem;

/**
 * 
 * @author Group 21
 *
 *         <p>
 *         The NPC class represents the basic framework for all non player
 *         character characters, for example wolves or shopkeepers. Each NPC
 *         requires an NPCDefinition, which defines the properties of the NPC.
 *         </p>
 */
public class NPC extends Character {
	private final NPCDefinition definition; // The NPC definition of this NPC
	private final int spawnX;
	private final int spawnZ;
	private final int spawnY;
	private final boolean autoRespawn;

	public NPC(int x, int y, int z, final NPCDefinition definition, int instanceNumber) {
		super(x, y, z, ClosedIDSystem.getTag(), definition.getID(), definition.getName());
		assert definition != null;
		this.spawnX = x;
		this.spawnY = y;
		this.spawnZ = z;
		this.definition = definition;
		this.instanceNumber = instanceNumber;
		this.autoRespawn = true;
	}

	public NPC(int x, int y, int z, final NPCDefinition definition, int instanceNumber, boolean autoRespawn) {
		super(x, y, z, ClosedIDSystem.getTag(), definition.getID(), definition.getName());
		assert definition != null;
		this.spawnX = x;
		this.spawnY = y;
		this.spawnZ = z;
		this.definition = definition;
		this.instanceNumber = instanceNumber;
		this.autoRespawn = autoRespawn;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.NPC;
	}

	@Override
	public Packet getExtensivePacket() {
		return new ExtensiveCharacterPacket(null, getUniqueID(), definition.getID(), x, y, getHealth(), getMaxHealth(),
				definition.getLevel(), definition.getName());
	}

	@Override
	public int getLevel() {
		return definition.getLevel();
	}

	@Override
	public int getMaxHealth() {
		return definition.getMaxHealth();
	}

	public int getRespawnTimer() {
		return definition.getRespawnTimer();
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public int getSpawnZ() {
		return spawnZ;
	}

	public boolean isAutoRespawn() {
		return autoRespawn && definition.isAutoRespawn();
	}

	@Override
	public void process() {
		//System.out.println("Processing " + this);
	}

	@Override
	public String toString() {
		return definition.getName() + ":" + getUniqueID();
	}

}
