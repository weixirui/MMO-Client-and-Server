package com.git.cs309.mmoserver.entity.characters;

import java.awt.EventQueue;
import java.util.Queue;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.entity.Entity;
import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.map.PathFinder;
import com.git.cs309.mmoserver.map.PathFinder.Tile;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;
import com.git.cs309.mmoserver.util.CycleQueue;

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
	protected transient volatile Queue<Tile> walkingQueue = new CycleQueue<>(0);
	protected transient volatile long walkingTick = 0;
	protected transient volatile boolean walking = false;
	protected transient volatile boolean inCombat = false;

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
	
	public boolean isWalking() {
		return walking;
	}

	public abstract int getLevel();

	public abstract int getMaxHealth();

	public boolean isDead() {
		return isDead || health <= 0;
	}

	public void kill() {
		isDead = true;
	}
	
	private final void handleWalking() {
		if (!walking && !walkingQueue.isEmpty()) {
			walking = true;
		}
		if (walking && !walkingQueue.isEmpty() && Main.getTickCount() - walkingTick >= Config.TICKS_PER_WALK) {
			walkingTick = Main.getTickCount();
			Tile t = walkingQueue.remove();
			setPosition(t.getX(), t.getY(), getZ());
		}
		if (walking && walkingQueue.isEmpty()) {
			walking = false;
		}
		if (!walking && walkingQueue.isEmpty() && !inCombat && (int) (Math.random() * Config.NPC_WALKING_RATE) == 1) {
			int newX = (int) (Config.MAX_WALKING_DISTANCE - (Math.random() * Config.MAX_WALKING_DISTANCE * 2));
			int newY = (int) (Config.MAX_WALKING_DISTANCE - (Math.random() * Config.MAX_WALKING_DISTANCE * 2));
			walkingQueue = PathFinder.getPathToPoint(MapHandler.getInstance().getMapContainingPosition(instanceNumber, getX(), getY(), getZ()), getX(), getY(), newX, newY);
		}
	}
	
	protected abstract void characterProcess();

	public final void process() {
		handleWalking();
		characterProcess();
	}

}
