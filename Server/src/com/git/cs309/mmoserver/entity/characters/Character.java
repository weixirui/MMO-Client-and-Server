package com.git.cs309.mmoserver.entity.characters;

import com.git.cs309.mmoserver.Main;
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
		// For deserialization only
	}

	public Character(final int x, final int y, final IDTag idTag, final int entityID, final String name) {
		super (x, y, idTag, entityID, name);
		Main.getCharacterManager().addCharacter(this);
	}

	public void addToManager() {
		Main.getCharacterManager().addCharacter(this); // Ensure that all classes extending character get added to the CharacterManager
	}

	public abstract void applyDamage(int damageAmount); // Abstract so subclasses can implement in their own way.

	public abstract void applyRegen(int regenAmount); // Abstract so subclasses can implement in their own way.

	//Release this object and return id tag to system.
	public final void cleanUp() {
		super.cleanUp();
		Main.getCharacterManager().removeCharacter(this);
	}

	public int getHealth() {
		return health;
	}

	public abstract int getMaxHealth();

	public boolean isDead() {
		return isDead || health <= 0;
	}

	public void kill() {
		isDead = true;
	}

	public abstract void process(); // Force implementations to create their own process method.
	
}
