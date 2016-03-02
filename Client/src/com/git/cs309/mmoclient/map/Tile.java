package com.git.cs309.mmoclient.map;

public final class Tile {
	private final int x, y;
	private final String spriteName;
	
	public Tile(final int x, final int y, final String spriteName) {
		this.x = x;
		this.y = y;
		this.spriteName = spriteName;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the spriteName
	 */
	public String getSpriteName() {
		return spriteName;
	}
}
