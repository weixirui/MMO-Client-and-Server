package com.git.cs309.mmoclient.map;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.git.cs309.mmoclient.entity.Entity;

public final class Map {
	private final MapDefinition definition;
	private final List<Entity> entities = new ArrayList<Entity>();

	public Map(final MapDefinition definition) {
		this.definition = definition;
	}

	public boolean containsPoint(final int x, final int y) {
		return getXOrigin() + getWidth() >= x && x >= getXOrigin() && getYOrigin() + getHeight() >= y
				&& y >= getYOrigin();
	}
	
	public void paint(Graphics g) {
		synchronized (entities) {
		entities.sort(new Comparator<Entity>() {

			@Override
			public int compare(Entity arg0, Entity arg1) {
				return arg0.getY() - arg1.getY();
			}
			
		});
		definition.paint(g);
		synchronized (entities) {
			for (Entity e : entities) {
				e.paint(g);
			}
		}
		}
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Map)) {
			return false;
		}
		Map otherMap = (Map) other;
		return otherMap.definition.equals(definition);
	}

	public Entity[] getEntities(final int x, final int y) {
		assert (containsPoint(x, y));
		List<Entity> entities = new ArrayList<>();
		for (Entity entity : this.entities) {
			if (entity.getX() == x && entity.getY() == y) {
				entities.add(entity);
			}
		}
		return entities.toArray(new Entity[entities.size()]);
	}

	public Entity getEntity(final int x, final int y) {
		assert (containsPoint(x, y));
		for (Entity entity : entities) {
			if (entity.getX() == x && entity.getY() == y) {
				return entity;
			}
		}
		return null;
	}
	
	public Entity getEntity(final int uniqueId) {
		for (Entity entity : entities) {
			if (entity.getUniqueID() == uniqueId) {
				return entity;
			}
		}
		throw new RuntimeException("No entity for uniqueId: "+uniqueId);
	}

	public int getHeight() {
		return definition.getHeight();
	}

	public int getWidth() {
		return definition.getWidth();
	}
	
	public final int globalToLocalX(int x) {
		return x - getXOrigin();
	}
	
	public final int globalToLocalY(int y) {
		return y - getYOrigin();
	}
	
	public final int localToGlobalX(int x) {
		return x + getXOrigin();
	}
	
	public final int localToGlobalY(int y) {
		return y + getYOrigin();
	}

	public final int getXOrigin() {
		return definition.getXOrigin();
	}

	public final int getYOrigin() {
		return definition.getYOrigin();
	}
	
	//For debug only
	public void printMap() {
		System.out.println(definition.getMapName());
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				boolean entity = false;
				for (Entity e : entities) {
					if (e.getX() == localToGlobalX(x) && e.getY() == localToGlobalY(y)) {
						System.out.print(e.getName().charAt(0));
						entity = true;
						break;
					}
				}
				if (entity) {
					continue;
				}
				System.out.print(".");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void putEntity(final Entity entity) {
		synchronized (entities) {
		assert (containsPoint(entity.getX(), entity.getY()));
		assert entity != null && !entities.contains(entity);
		entities.add(entity);
		}
	}

	public void removeEntity(final int x, final int y) {
		synchronized (entities) {
		assert (containsPoint(x, y));
		Entity entity = getEntity(x, y);
		assert entity != null;
		entities.remove(entity);
		}
	}
}
