package com.git.cs309.mmoserver.characters.npc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;

public final class NPCManager {
	private static final List<NPCDefinition> NPC_DEFINITIONS = new ArrayList<>(); // List of NPC definitions.

	public static void initialize() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(Config.NPC_DEFINITION_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Main.requestExit();
		}
		int state = 0; // 0 - NPC, 1 - BOSS
		String line = "";
		try {
			while (!(line = reader.readLine()).startsWith("[EOF]")) {
				if (line.startsWith("[NPC]")) {
					state = 0;
					continue;
				}
				if (line.startsWith("[BOSS]")) {
					state = 1;
					continue;
				}
				if (line.startsWith("npc")) {
					// Later, use switch on state. Adding BOSS is just future proofing because I can totally see bosses having some unique stuff.
					String[] tokens = line.split(" ");
					try {
						NPC_DEFINITIONS.add(new NPCDefinition(tokens[1], Integer.parseInt(tokens[2]),
								Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]),
								Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7])));
					} catch (NumberFormatException e) {
						e.printStackTrace();
						continue;
					} catch (AssertionError e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
