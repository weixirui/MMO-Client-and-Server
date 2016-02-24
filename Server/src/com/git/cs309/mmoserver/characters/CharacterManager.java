package com.git.cs309.mmoserver.characters;

import java.util.HashSet;
import java.util.Set;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.util.TickProcess;

/**
 * 
 * @author Group 21
 *
 *         TickProcess implementation that manages all Character objects.
 */
public final class CharacterManager extends TickProcess {

	private final Set<Character> characterSet = new HashSet<>(); // All registered characters

	public CharacterManager() {
		super("CharacterManager");
		CharacterManager predecessor = Main.getCharacterManager();
		if (predecessor != null) {
			characterSet.addAll(predecessor.characterSet);
			predecessor.forceStop();
		}
		predecessor = null;
	}

	/**
	 * Adds a character object to the character set. In theory, this should only
	 * need to be called from the Character constructor.
	 * 
	 * @param character
	 *            character to add to set.
	 */
	public synchronized void addCharacter(final Character character) { // Add new character to characterSet
		characterSet.add(character);
	}

	@Override
	public void ensureSafeClose() {
		//Not required
	}

	/**
	 * Removes character from the set.
	 * 
	 * @param character
	 *            character to remove from set.
	 */
	public synchronized void removeCharacter(final Character character) { // Remove character from set
		characterSet.remove(character);
	}

	/**
	 * Processes characters. Should be called each tick, from the tickTask
	 * method.
	 * 
	 * @param regenTick
	 *            determines whether or not to apply regeneration this tick.
	 */
	private void processCharacters(final boolean regenTick) {
		synchronized (characterSet) {
			for (Character character : characterSet) {
				if (regenTick) {
					character.applyRegen(Config.REGEN_AMOUNT);
				}
				character.process();
			}
		}
	}

	@Override
	protected void tickTask() {
		processCharacters(Main.getTickCount() % Config.TICKS_PER_REGEN == 0); // Process characters.
	}

}
