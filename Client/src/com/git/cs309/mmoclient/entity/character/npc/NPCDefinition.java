package com.git.cs309.mmoclient.entity.character.npc;

/**
 * 
 * @author Group 21
 *
 *         <p>
 *         The NPCDefinition is a class that houses the traits for each NPC in
 *         the game. Each NPC has their own NPCDefinition, and multiple
 *         instances of the same NPC will share the same NPCDefinition instance.
 *         </p>
 */
public final class NPCDefinition {
	private final int id;
	//String name of the NPC
	private final String name;

	public NPCDefinition(final String name, final int id) {
		this.name = name;
		this.id = id;
	}

	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
}
