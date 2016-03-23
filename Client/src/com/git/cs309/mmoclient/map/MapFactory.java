package com.git.cs309.mmoclient.map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.git.cs309.mmoclient.Config;

public final class MapFactory {
	private static final MapFactory INSTANCE = new MapFactory();

	public static final MapFactory getInstance() {
		return INSTANCE;
	}

	private final HashMap<String, MapDefinition> mapDefinitions = new HashMap<>();

	private MapFactory() {
		loadDefinitions();
	}

	public synchronized final Map createMap(final String mapName) {
		MapDefinition definition = mapDefinitions.get(mapName.toLowerCase());
		if (definition == null) {
			throw new RuntimeException("No map definition for map name: " + mapName);
		}
		return new Map(definition);
	}

	public synchronized void loadDefinitions() {
		mapDefinitions.clear();
		File folder = new File(Config.MAP_CONFIG_FOLDER);
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
