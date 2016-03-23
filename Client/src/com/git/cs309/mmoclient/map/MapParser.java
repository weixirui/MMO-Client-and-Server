package com.git.cs309.mmoclient.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class MapParser {
	public static final byte SERVER_ONLY = 0;
	public static final byte BACKGROUND = 1;
	
	private static final class SpawnCharacter {
		private final char character;
		private final String name;
		private final byte type;

		public SpawnCharacter(final char character, final String name, final byte type) {
			this.character = character;
			this.name = name;
			this.type = type;
		}
	}
	//Reserved Characters:
	public static final char EMPTY_SPACE = ' ';
	public static final char COORDINATE_SEPARATOR = '|';

	public static final char MULTIPLE_SPAWN_SEPARATOR = ',';

	public static final MapDefinition parseMapDefinitionFromFile(final File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File cannot be null.");
		BufferedReader fileReader = new BufferedReader(new FileReader(file));
		HashMap<Character, SpawnCharacter> spawnChars = new HashMap<>();
		Set<Tile> backgroundTiles = new HashSet<Tile>();
		String name = "Null";
		int width = 0;
		int height = 0;
		int xOrigin = 0;
		int yOrigin = 0;
		String line = "";
		int lineNumber = 0;
		int y = 0;
		while ((line = fileReader.readLine()) != null) {
			lineNumber++;
			if (line.startsWith("//")) {
				continue;
			}
			if (line.startsWith("name := ")) {
				name = line.replace("name := ", "");
				continue;
			}
			try {
				if (line.startsWith("width := ")) {
					width = Integer.parseInt(line.replace("width := ", ""));
					continue;
				}
				if (line.startsWith("height := ")) {
					height = Integer.parseInt(line.replace("height := ", ""));
					continue;
				}
				if (line.startsWith("x := ")) {
					xOrigin = Integer.parseInt(line.replace("x := ", ""));
					continue;
				}
				if (line.startsWith("y := ")) {
					yOrigin = Integer.parseInt(line.replace("y := ", ""));
					continue;
				}
			} catch (NumberFormatException e) {
				fileReader.close();
				throw new RuntimeException(file.getName() + " contains number format exception on line " + lineNumber);
			}
			if (line.contains(":=") && !line.contains("z := ")) { // Spawn chars
				try {
					SpawnCharacter sc = spawnCharacterFromLine(line);
					spawnChars.put(sc.character, sc);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
				continue;
			}
			line = line.replace("" + COORDINATE_SEPARATOR, "");
			char[] chars = line.toCharArray();
			for (int x = 0, i = 0; x < width && i < chars.length; i++, x++) {
				if (chars[i] == MULTIPLE_SPAWN_SEPARATOR) {
					x -= 2;
					continue;
				}
				if (chars[i] == EMPTY_SPACE) {
					continue;
				}
				SpawnCharacter thisSpawn = spawnChars.get(chars[i]);
				if (thisSpawn == null) {
					continue;
				}
				switch (thisSpawn.type) {
				case BACKGROUND:
					backgroundTiles.add(new Tile(x + xOrigin, y + yOrigin, thisSpawn.name));
					break;
				}
			}
			y++;
		}
		fileReader.close();
		if (spawnChars.size() == 0) {
			outlineMap(file, width, height);
		}
		return new MapDefinition(name, xOrigin, yOrigin, width, height, backgroundTiles);
	}

	private static final void outlineMap(final File file, final int width, final int height) throws IOException {
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				fileWriter.write("| ");
			}
			fileWriter.write("|");
			fileWriter.newLine();
		}
		fileWriter.close();
	}

	private static final SpawnCharacter spawnCharacterFromLine(String line) {
		try {
			char character = line.charAt(0);
			String[] tokens = line.split(" ");
			//w := Wolf npc
			String cname = tokens[2].replace("_", " ");
			switch (tokens[3].toLowerCase()) {
			case "npc":
				return new SpawnCharacter(character, cname, SERVER_ONLY);
			case "obj":
				return new SpawnCharacter(character, cname, SERVER_ONLY);
			case "nul":
				return new SpawnCharacter(character, cname, BACKGROUND);
			default:
				System.err.println("No case for SpawnCharacter type: " + tokens[3]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Could not create spawn character from line: " + line);
	}
}
