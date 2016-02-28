package com.git.cs309.mmoserver.map;

import java.io.Serializable;
import java.util.Collection;

public final class MapDefinition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3174752973855574603L;
	private final String mapName;
	private final int width;
	private final int height;
	private final int xOrigin;
	private final int yOrigin;
	private final int z;
	private final Collection<Spawn> spawns;

	public MapDefinition(final String mapName, final int xOrigin, final int yOrigin, final int z, final int width,
			final int height, final Collection<Spawn> spawns) {
		this.mapName = mapName;
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.z = z;
		this.width = width;
		this.height = height;
		this.spawns = spawns;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MapDefinition)) {
			return false;
		}
		MapDefinition map = (MapDefinition) other;
		return map.xOrigin == xOrigin && map.yOrigin == yOrigin && map.z == z && map.width == width
				&& map.height == height;
	}

	public int getHeight() {
		return height;
	}

	public final String getMapName() {
		return mapName;
	}

	public Collection<Spawn> getSpawns() {
		return spawns;
	}

	public final int getWidth() {
		return width;
	}

	public int getxOrigin() {
		return xOrigin;
	}

	public int getyOrigin() {
		return yOrigin;
	}

	public int getZ() {
		return z;
	}
}
