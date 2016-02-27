package com.git.cs309.mmoserver.map;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.git.cs309.mmoserver.Config;

public final class MapFactory {
	private static final MapFactory SINGLETON = new MapFactory(Config.MAP_DEFINITIONS_FOLDER);

	public static final MapFactory getInstance() {
		return SINGLETON;
	}
	
	public static final MapFactory newInstance(final String mapFolder) {
		return new MapFactory(mapFolder);
	}
	
	private final HashMap<String, MapDefinition> mapDefinitions = new HashMap<>();
	
	private MapFactory(final String mapFolder) {
		loadDefinitions(mapFolder);
	}
	
	private void loadDefinitions(final String mapFolder) {
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
				mapDefinitions.put(definition.getMapName(), definition);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public final Map createMap(final String mapName, final int instanceNumber) {
		
	}
}
