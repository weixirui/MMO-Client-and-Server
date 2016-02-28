package com.git.cs309.mmoserver.entity.characters;

import java.awt.EventQueue;

import com.git.cs309.mmoserver.entity.Entity;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

/**
 * 
 * @author Group 21
 *
 *         An abstract class representing the basis for all in-game characters,
 *         such as player, npcs, bosses, etc.
 */
public abstract class Character extends Entity {

	//Current health.
	protected volatile int health;
	protected volatile boolean isDead; //true is dead

	public Character() {
		super();
	}

	public Character(final int x, final int y, final int z, final IDTag idTag, final int entityID, final String name) {
		super(x, y, z, idTag, entityID, name);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				CharacterManager.getInstance().addCharacter(Character.this);
			}

		});
	}

	public void applyDamage(int damageAmount) {
		health -= damageAmount;
		if (health <= 0) {
			isDead = true;
		}
	}

	public void applyRegen(int regenAmount) {
		if (isDead) {
			return;
		}
		if (health + regenAmount <= getMaxHealth()) {
			health += regenAmount;
		} else {
			health = getMaxHealth();
		}
	}

	@Override
	public boolean canWalkThrough() {
		return true;
	}

	public int getHealth() {
		return health;
	}

	public abstract int getLevel();

	public abstract int getMaxHealth();

	public boolean isDead() {
		return isDead || health <= 0;
	}

	public void kill() {
		isDead = true;
	}

	public abstract void process(); // Force implementations to create their own process method.

}
