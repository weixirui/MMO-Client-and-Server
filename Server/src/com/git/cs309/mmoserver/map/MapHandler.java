package com.git.cs309.mmoserver.map;

import java.util.HashSet;
import java.util.Set;

import com.git.cs309.mmoserver.entity.Entity;

public final class MapHandler {
	//I'm not certain how I want to implement this yet.
	private static final Set<Map> MAPS = new HashSet<>();
	
	private MapHandler() {
		//Prevent instantiation
	}
	
	public static final Map getMapContainingPosition(final int x, final int y, final int z) {
		for (Map map : MAPS) {
			if (map.getZ() == z && map.containsPoint(x, y)) {
				return map;
			}
		}
		return null;
	}
	
	public static final Entity getEntityAtPosition(final int x, final int y, final int z, final Entity entity) {
		Map map = getMapContainingPosition(x, y, z);
		if (map == null) {
			//Maybe TRY and create a new map
			return null;
		}
		return map.getEntityOnGlobal(x, y);
	}
	
	public static final void setEntityAtPosition(final int x, final int y, final int z, final Entity entity) {
		Map map = getMapContainingPosition(x, y, z);
		if (map == null) {
			//Maybe TRY and create a new map
			return;
		}
		assert(map.getEntityOnGlobal(x, y) == null); // Cannot place an entity where there is already an entity
		map.setEntityOnGlobal(x, y, entity);
	}
}
