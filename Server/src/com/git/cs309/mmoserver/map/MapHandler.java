package com.git.cs309.mmoserver.map;

import java.util.HashSet;
import java.util.Set;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.entity.Entity;

public final class MapHandler {
	private static final MapHandler INSTANCE = new MapHandler();

	public static final MapHandler getInstance() {
		return INSTANCE;
	}

	private final Set<Map> maps = new HashSet<>();

	private MapHandler() {
		//Nothing here, since can't load maps because of semantics
	}

	public final Entity getEntityAtPosition(final int instanceNumber, final int x, final int y, final int z) {
		Map map = getMapContainingPosition(instanceNumber, x, y, z);
		if (map == null) {
			return null;
		}
		return map.getEntity(x, y);
	}

	public final Map getMapContainingPosition(final int instanceNumber, final int x, final int y, final int z) {
		for (Map map : maps) {
			if (map.getZ() == z && map.containsPoint(x, y) && map.getInstanceNumber() == instanceNumber) {
				return map;
			}
		}
		throw new RuntimeException("No map for position.");
	}
	
	public final Map getMapContainingEntity(final Entity entity) {
		for (Map map : maps) {
			if (map.getZ() == entity.getZ() && map.containsPoint(entity.getX(), entity.getY()) && map.getInstanceNumber() == entity.getInstanceNumber()) {
				return map;
			}
		}
		throw new RuntimeException("Entity is not in a map.");
	}

	public final void loadMaps() {
		maps.clear();
		addMap(MapFactory.getInstance().createMap("island", Config.GLOBAL_INSTANCE));
		for (Map map : maps) {
			map.loadSpawns();
		}
	}
	
	public final Map createInstanceMap(int instanceNumber, String mapName) {
		Map map = MapFactory.getInstance().createMap(mapName, instanceNumber);
		if (map == null) {
			throw new RuntimeException("No map definition for map name \""+mapName+"\"");
		}
		return map;
	}

	public final void moveEntity(final int uniqueId, final int oInstanceNumber, final int oX, final int oY, final int oZ,
			final int dInstanceNumber, final int dX, final int dY, final int dZ) {
		Map map = getMapContainingPosition(oInstanceNumber, oX, oY, oZ);
		if (!map.equals(getMapContainingPosition(dInstanceNumber, dX, dY, dZ))) {
			Map newMap = getMapContainingPosition(dInstanceNumber, dX, dY, dZ);
			Entity e = map.getEntity(uniqueId, oX, oY);
			map.removeEntity(oX, oY, e);
			newMap.putEntity(dX, dY, e);
			return;
		}
		map.moveEntity(uniqueId, oX, oY, dX, dY);
	}

	public final void putEntityAtPosition(final int instanceNumber, final int x, final int y, final int z,
			final Entity entity) {
		Map map = getMapContainingPosition(instanceNumber, x, y, z);
		if (map == null) {
			return;
		}
		map.putEntity(x, y, entity);
	}

	public final void removeEntityAtPosition(final int instanceNumber, final int x, final int y, final int z, final Entity entity) {
		Map map = getMapContainingPosition(instanceNumber, x, y, z);
		if (map == null) {
			return;
		}
		assert (map.getEntity(x, y) != null);
		map.removeEntity(x, y, entity);
	}

	final void addMap(Map map) {
		if (maps.contains(map)) {
			return;
		}
		maps.add(map);
	}

	final void removeMap(Map map) {
		if (maps.contains(map))
			maps.remove(map);
	}
}
