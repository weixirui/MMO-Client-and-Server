package com.git.cs309.mmoserver.map;

import java.util.HashSet;
import java.util.Set;

import com.git.cs309.mmoserver.entity.Entity;

public final class MapHandler {
	//I'm not certain how I want to implement this yet.
	private static final Set<Map> MAPS = new HashSet<>();

	public static final Entity getEntityAtPosition(final int instanceNumber, final int x, final int y, final int z) {
		Map map = getMapContainingPosition(instanceNumber, x, y, z);
		if (map == null) {
			//Maybe TRY and create a new map
			return null;
		}
		return map.getEntity(x, y);
	}

	public static final Map getMapContainingPosition(final int instanceNumber, final int x, final int y, final int z) {
		for (Map map : MAPS) {
			if (map.getZ() == z && map.containsPoint(x, y) && map.getInstanceNumber() == instanceNumber) {
				return map;
			}
		}
		return null;
	}

	public static final void moveEntity(final int oInstanceNumber, final int oX, final int oY, final int oZ,
			final int dInstanceNumber, final int dX, final int dY, final int dZ) {
		Map map = getMapContainingPosition(oInstanceNumber, oX, oY, oZ);
		if (!map.equals(getMapContainingPosition(dInstanceNumber, dX, dY, dZ))) {
			Map newMap = getMapContainingPosition(dInstanceNumber, dX, dY, dZ);
			Entity e = map.getEntity(oX, oY);
			map.putEntity(oX, oY, null);
			newMap.putEntity(dX, dY, e);
			return;
		}
		map.moveEntity(oX, oY, dX, dY);
	}

	public static final void setEntityAtPosition(final int instanceNumber, final int x, final int y, final int z,
			final Entity entity) {
		Map map = getMapContainingPosition(instanceNumber, x, y, z);
		if (map == null) {
			//Maybe TRY and create a new map
			return;
		}
		assert (map.getEntity(x, y) == null); // Cannot place an entity where there is already an entity
		map.putEntity(x, y, entity);
	}

	private MapHandler() {
		//Prevent instantiation
	}
}
