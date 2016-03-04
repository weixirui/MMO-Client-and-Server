package com.git.cs309.mmoclient.entity.character;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoclient.entity.Entity;

/**
 * 
 * @author Group 21
 *
 *         An abstract class representing the basis for all in-game characters,
 *         such as player, npcs, bosses, etc.
 */
public abstract class Character extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4707730413469521334L;

	public static final int NO_OPPONENT = -1;
	
	//Current health.
	protected volatile int health = 0;
	protected volatile int maxHealth = 0;
	protected volatile int level = 0;
	protected volatile boolean isDead = false; //true is dead
	protected volatile boolean walking = false;
	protected volatile boolean inCombat = false;
	protected volatile int opponentId = -1;

	public Character(final int x, final int y, final int uniqueId, final int entityID, final String name) {
		super(x, y, uniqueId, entityID, name);
	}
	
	public void resetCombat() {
		inCombat = false;
		opponentId = NO_OPPONENT;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public int getHealth() {
		return health;
	}
	
	public boolean isWalking() {
		return walking;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public boolean isDead() {
		return isDead || health <= 0;
	}
	
	public void setOponentId(final int opponentId) {
		this.opponentId = opponentId;
		if (opponentId == NO_OPPONENT) {
			inCombat = false;
		} else {
			inCombat = true;
		}
	}

}
