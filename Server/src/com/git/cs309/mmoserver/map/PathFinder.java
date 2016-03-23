package com.git.cs309.mmoserver.map;

import java.util.Queue;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.util.CycleQueue;
import com.git.cs309.mmoserver.util.MathUtils;

public final class PathFinder {
	
	public static final int START = 0;
	public static final int EMPTY = -1;
	public static final int CANT_WALK = -2;
	public static final int DESTINATION = -3;
	
	public static final class Tile {
		private final int x, y;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Tile)) {
				return false;
			}
			Tile cell = (Tile) other;
			return cell.x == x && cell.y == y;
		}
	}

	public static final Queue<Tile> getPathToPoint(final Map map, final int x1, final int y1, final int x2,
			final int y2) {
		return getPath(map, x1, y1, x2, y2);
	}

	//Uses an implementation of Lee's algorithm
	private static final Queue<Tile> getPath(final Map map, final int x1, final int y1, final int x2, final int y2) {
		int[][] grid = map.getPathingMap();
		int originX = (x1 - map.getXOrigin());
		int originY = (y1 - map.getYOrigin());
		if (grid[x2 - map.getXOrigin()][y2 - map.getXOrigin()] != EMPTY) {
			return new CycleQueue<Tile>(0);
		}
		grid[x2 - map.getXOrigin()][y2 - map.getXOrigin()] = DESTINATION;
		grid[originX][originY] = START;
		boolean stop = false;
		int sX;
		int sY;
		int eX;
		int eY;
		int sX2;
		int sY2;
		int eX2;
		int eY2;
		int step;
		for (step = 0; !stop && step < Config.MAX_WALKING_DISTANCE ; step++) {
			sX = originX - step;
			sY = originY - step;
			eX = originX + step;
			eY = originY + step;
			for (int x = sX; x <= eX; x++) {
				for (int y = sY; y <= eY; y++) {
					if ((x != sX && x != eX) && (y != sY && y != eY)) {
						continue;
					}
					sX2 = x - 1;
					sY2 = y - 1;
					eX2 = x + 1;
					eY2 = y + 1;
					for (int x3 = sX2; x3 <= eX2; x3++) {
						for (int y3 = sY2; y3 <= eY2; y3++) {
							if (x3 - 1 < 0 || y3 - 1 < 0 || x3 >= grid.length || y3 >= grid[x3].length) {
								continue;
							}
							if (grid[x3][y3] == DESTINATION) {
								stop = true;
								continue;
							}
							if (grid[x3][y3] != EMPTY) {
								continue;
							}
							grid[x3][y3] = step + 1;
						}
					}
				}
			}
		}
		if (!stop) {
			return new CycleQueue<Tile>(0); //Empty List
		}
		step -= 1;
		int lX = (x2 - map.getXOrigin());
		int lY = (y2 - map.getYOrigin());
		double closestDistance;
		int tX = 0;
		int tY = 0;
		double distance;
		CycleQueue<Tile> walkingQueue = new CycleQueue<>(step + 1);
		walkingQueue.add(new Tile(x2, y2));
		while (step > 0) {
			closestDistance = grid.length * 2;
			for (int x = lX - 1; x <= lX + 1; x++) {
				for (int y = lY - 1; y <= lY + 1; y++) {
					if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || grid[x][y] > step || grid[x][y] < 0) {
						continue;
					}
					distance = MathUtils.distance(x, y, originX, originY);
					if (distance < closestDistance) {
						tX = x;
						tY = y;
						closestDistance = distance;
					}
				}
			}
			if (closestDistance == grid.length * 2) {
				if (Main.isDebug()) {
					System.err.println("Error: Returned walking queue because no tile was found in step");
				}
				return new CycleQueue<Tile>(0);
			}
			walkingQueue.add(new Tile(tX + map.getXOrigin(), tY + map.getYOrigin()));
			lX = tX;
			lY = tY;
			step--;
		}
		walkingQueue.reverse();
		return walkingQueue;
	}

}
