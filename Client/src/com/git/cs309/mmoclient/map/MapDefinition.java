package com.git.cs309.mmoclient.map;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Set;

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
	private final Tile[][] background;

	public MapDefinition(final String mapName, final int xOrigin, final int yOrigin, final int width,
			final int height, final Set<Tile> backgroundTiles) {
		this.mapName = mapName;
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.width = width;
		this.height = height;
		background = new Tile[width][height];
		for (Tile tile : backgroundTiles) {
			background[tile.getX() - xOrigin]
					[tile.getY() - yOrigin] = tile;
		}
	}
	
	public void paint(Graphics g) {
		//Paint background.
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MapDefinition)) {
			return false;
		}
		MapDefinition map = (MapDefinition) other;
		return map.mapName.equalsIgnoreCase(mapName) && map.xOrigin == xOrigin && map.yOrigin == yOrigin && map.width == width
				&& map.height == height;
	}

	public int getHeight() {
		return height;
	}

	public final String getMapName() {
		return mapName;
	}

	public final int getWidth() {
		return width;
	}

	public int getXOrigin() {
		return xOrigin;
	}

	public int getYOrigin() {
		return yOrigin;
	}
}
