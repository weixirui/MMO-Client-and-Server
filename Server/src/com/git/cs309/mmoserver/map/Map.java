package com.git.cs309.mmoserver.map;

import com.git.cs309.mmoserver.entity.Entity;

public final class Map {
	private final int instanceNumber;
	private final int xOrigin; // (0, 0) on this graphs actual X value as far as whole map is concerned
	private final int yOrigin; // (0, 0) on this graphs actual Y value as far as whole map is concerned
	private final int z;
	private final int width;
	private final int height;
	private final Entity[][] entityMap;
	
	public Map(final int instanceNumber, final int xOrigin, final int yOrigin, final int z, final int width, final int height) {
		this.instanceNumber = instanceNumber;
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.z = z;
		this.width = width;
		this.height = height;
		entityMap = new Entity[width][height];
		setMapToNulls();
	}
	
	private void setMapToNulls() {
		for (int i = 0; i < entityMap.length; i++) {
			for (int j = 0; j < entityMap[i].length; j++) {
				entityMap[i][j] =null;
			}
		}
	}
	
	public boolean containsPoint(final int x, final int y) {
		return xOrigin + width >= x && x >= xOrigin && yOrigin + height >= y && y >= yOrigin;
	}
	
	public void setEntityOnGlobal(final int x, final int y, final Entity entity) {
		assert (containsPoint(x, y));
		entityMap[x - xOrigin][y - yOrigin] = entity;
	}
	
	public Entity getEntityOnGlobal(final int x, final int y) {
		assert (containsPoint(x, y));
		return entityMap[x - xOrigin][y - yOrigin];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setEntityOnMap(final int x, final int y, final Entity entity) {
		assert (x >= 0 && x < width && y >= 0 && y < height);
		entityMap[x][y] = entity;
	}
	
	public Entity getEntityOnMap(final int x, final int y) {
		assert (x >= 0 && x < width && y >= 0 && y < height);
		return entityMap[x][y];
	}
	
	public final int getXOrigin() {
		return xOrigin;
	}
	
	public final int getYOrigin() {
		return yOrigin;
	}
	
	public final int getInstanceNumber() {
		return instanceNumber;
	}
}
