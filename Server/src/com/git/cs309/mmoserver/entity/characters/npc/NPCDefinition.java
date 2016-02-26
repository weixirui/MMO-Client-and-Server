package com.git.cs309.mmoserver.entity.characters.npc;

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
	//Accuracy of the NPC on a scale of 0-100
	private final int accuracy;
	//Defense of the NPC on a scale of 0-Integer.MAX_VALUE
	private final int defence;
	//Static ID of the NPC
	private final int id;
	//Level of the NPC, on a scale of 1-Integer.MAX_VALUE
	private final int level;
	//Max health of the npc, on a scale of 1 - Integer.MAX_VALUE
	private final int maxHealth;
	//String name of the NPC
	private final String name;
	//Strength of the NPC, on a scale of 0 - Integer.MAX_VALUE
	private final int strength;

	public NPCDefinition(final String name, final int id, final int maxHealth, final int strength, final int accuracy,
			final int defence, final int level) {
		this.name = name;
		this.id = id;
		this.maxHealth = maxHealth;
		this.strength = strength;
		this.accuracy = accuracy;
		this.defence = defence;
		this.level = level;
		assert (id >= 0) && (maxHealth > 0) && (strength >= 0) && (accuracy >= 0 && accuracy <= 100) && (defence >= 0)
				&& (level > 0);
	}

	public int getAccuracy() {
		return accuracy;
	}

	public int getDefence() {
		return defence;
	}

	public int getID() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public String getName() {
		return name;
	}

	public int getStrength() {
		return strength;
	}
}
