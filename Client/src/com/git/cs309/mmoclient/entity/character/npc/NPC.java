package com.git.cs309.mmoclient.entity.character.npc;

import com.git.cs309.mmoclient.entity.EntityType;
import com.git.cs309.mmoclient.entity.character.Character;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = 7665018239684872294L;
	private transient final NPCDefinition definition; // The NPC definition of this NPC

	public NPC(int x, int y, final NPCDefinition definition, final int uniqueId) {
		super(x, y, uniqueId, definition.getID(), definition.getName());
		assert definition != null;
		this.definition = definition;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.NPC;
	}

	@Override
	public String toString() {
		return definition.getName() + ":" + getUniqueID();
	}

}
