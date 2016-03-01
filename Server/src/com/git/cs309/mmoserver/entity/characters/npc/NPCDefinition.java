package com.git.cs309.mmoserver.entity.characters.npc;

import com.git.cs309.mmoserver.combat.CombatStyle;

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
	//Should NPCs with this definition be automatically respawned.
	private final boolean autoRespawn;
	//Time in minutes before NPC respawns
	private final int respawnTimer;
	//Will walk (false, will just stay in place)
	private final boolean canWalk;
	//Will it attack others
	private final boolean aggressive;
	//Attack style
	private final CombatStyle combatStyle;

	public NPCDefinition(final String name, final int id, final int maxHealth, final int strength, final int accuracy,
			final int defence, final int level, final boolean autoRespawn, final int respawnTimer, final boolean canWalk, final boolean aggressive, final CombatStyle combatStyle) {
		this.name = name;
		this.id = id;
		this.maxHealth = maxHealth;
		this.strength = strength;
		this.accuracy = accuracy;
		this.defence = defence;
		this.level = level;
		this.autoRespawn = autoRespawn;
		this.respawnTimer = respawnTimer;
		this.canWalk = canWalk;
		this.aggressive = aggressive;
		this.combatStyle = combatStyle;
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

	public int getRespawnTimer() {
		return respawnTimer;
	}

	public int getStrength() {
		return strength;
	}

	public boolean isAutoRespawn() {
		return autoRespawn;
	}

	/**
	 * @return the canWalk
	 */
	public boolean canWalk() {
		return canWalk;
	}
	
	public boolean aggressive() {
		return aggressive;
	}

	/**
	 * @return the combatStyle
	 */
	public CombatStyle getCombatStyle() {
		return combatStyle;
	}
}
