package com.git.cs309.mmoserver.entity.characters.npc;

import com.git.cs309.mmoserver.entity.EntityType;
/**
 *
 * @author Group 21
 * 
 */
import com.git.cs309.mmoserver.entity.characters.Character;
import com.git.cs309.mmoserver.util.ClosedIDSystem;

public class NPC extends Character {
	private final NPCDefinition definition;

	public NPC(int x, int y, int z, final NPCDefinition definition, int instanceNumber) {
		super(x, y, z, ClosedIDSystem.getTag(), definition.getID(), definition.getName());
		this.definition = definition;
		this.instanceNumber = instanceNumber;
	}

	@Override
	public int getMaxHealth() {
		return definition.getMaxHealth();
	}

	@Override
	public void process() {
		//System.out.println("Processing " + this);
	}

	@Override
	public String toString() {
		return definition.getName() + ":" + getUniqueID();
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.NPC;
	}

}
