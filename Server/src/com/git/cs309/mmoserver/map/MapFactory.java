package com.git.cs309.mmoserver.map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.git.cs309.mmoserver.Config;

public final class MapFactory {
	private static final MapFactory INSTANCE = new MapFactory(Config.MAP_DEFINITIONS_FOLDER);

	public static final MapFactory getInstance() {
		return INSTANCE;
	}

	private final HashMap<String, MapDefinition> mapDefinitions = new HashMap<>();
	private final String mapFolder;

	private MapFactory(final String mapFolder) {
		this.mapFolder = mapFolder;
		loadDefinitions();
	}

	public synchronized final Map createMap(final String mapName, final int instanceNumber) {
		MapDefinition definition = mapDefinitions.get(mapName.toLowerCase());
		if (definition == null) {
			throw new RuntimeException("No map definition for map name: " + mapName);
		}
		return new Map(definition, instanceNumber);
	}

	public synchronized void loadDefinitions() {
		mapDefinitions.clear();
		File folder = new File(mapFolder);
		for (File mapFile : folder.listFiles()) {
			if (!mapFile.getName().endsWith(".map")) {
				continue;
			}
			try {
				MapDefinition definition = MapParser.parseMapDefinitionFromFile(mapFile);
				if (definition == null) {
					continue;
				}
				mapDefinitions.put(definition.getMapName().toLowerCase(), definition);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}
}
