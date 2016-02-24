package com.git.cs309.mmoserver.characters.npc;

/**
 *
 * @author Group 21
 * 
 */

import com.git.cs309.mmoserver.characters.Character;
import com.git.cs309.mmoserver.util.ClosedIDSystem;

public class NPC extends Character {
	private final NPCDefinition definition;

	public NPC(int x, int y, final NPCDefinition definition) {
		super(x, y, ClosedIDSystem.getTag());
		this.definition = definition;
	}

	@Override
	public void applyDamage(int damageAmount) {
		health -= damageAmount;
		if (health <= 0) {
			isDead = true;
		}
	}

	@Override
	public void applyRegen(int regenAmount) {
		if (health + regenAmount <= getMaxHealth()) {
			health += regenAmount;
		}
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

}
