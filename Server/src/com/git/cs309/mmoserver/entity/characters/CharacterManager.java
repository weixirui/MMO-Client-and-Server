package com.git.cs309.mmoserver.entity.characters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.cycle.CycleProcess;
import com.git.cs309.mmoserver.cycle.CycleProcessManager;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.entity.characters.npc.NPC;
import com.git.cs309.mmoserver.entity.characters.npc.NPCFactory;
import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.util.TickProcess;

/**
 * 
 * @author Group 21
 *
 *         TickProcess implementation that manages all Character objects.
 */
public final class CharacterManager extends TickProcess {
	private static final CharacterManager INSTANCE = new CharacterManager();

	public static final CharacterManager getInstance() {
		return INSTANCE;
	}

	private final Set<Character> characterSet = new HashSet<>(); // All registered characters

	private CharacterManager() {
		super("CharacterManager");
	}

	/**
	 * Adds a character object to the character set. In theory, this should only
	 * need to be called from the Character constructor.
	 * 
	 * @param character
	 *            character to add to set.
	 */
	public void addCharacter(final Character character) { // Add new character to characterSet
		synchronized (characterSet) {
			characterSet.add(character);
			MapHandler.getInstance().putEntityAtPosition(character.getInstanceNumber(), character.getX(),
					character.getY(), character.getZ(), character);
		}
	}

	@Override
	public void ensureSafeClose() {
		//Not required
	}

	@Override
	public void printStatus() {
		println("Total characters: " + characterSet.size());
	}

	public void removeCharacter(final Character character) {
		synchronized (characterSet) {
			characterSet.remove(character);
			MapHandler.getInstance().removeEntityAtPosition(character.getInstanceNumber(), character.getX(),
					character.getY(), character.getZ());
			if (character.getEntityType() == EntityType.NPC && ((NPC) character).isAutoRespawn()) {
				CycleProcessManager.getInstance().addProcess(new CycleProcess() {
					final long startTick = Main.getTickCount();
					long currentTick = Main.getTickCount();
					final NPC npc = (NPC) character;

					@Override
					public void end() {
						NPCFactory.getInstance().createNPC(npc.getName(), npc.getSpawnX(), npc.getY(), npc.getZ(),
								npc.getInstanceNumber());
					}

					@Override
					public boolean finished() {
						return currentTick - startTick == (Config.TICKS_PER_MINUTE * npc.getRespawnTimer());
					}

					@Override
					public void process() {
						currentTick = Main.getTickCount();
					}

				});
			}
		}
	}

	/**
	 * Processes characters. Should be called each tick, from the tickTask
	 * method.
	 * 
	 * @param regenTick
	 *            determines whether or not to apply regeneration this tick.
	 */
	private void processCharacters(final boolean regenTick) {
		List<Character> toRemove = new ArrayList<>(characterSet.size());
		synchronized (characterSet) {
			for (Character character : characterSet) {
				if (character.needsDisposal()) {
					toRemove.add(character);
					continue;
				}
				if (regenTick) {
					character.applyRegen(Config.REGEN_AMOUNT);
				}
				character.process();
			}
		}
		for (Character character : toRemove) {
			removeCharacter(character);
		}
	}

	@Override
	protected void tickTask() {
		processCharacters(Main.getTickCount() % Config.TICKS_PER_REGEN == 0); // Process characters.
	}

}
