package com.git.cs309.mmoserver.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.connection.Connection;
import com.git.cs309.mmoserver.entity.Entity;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.entity.characters.user.PlayerCharacter;
import com.git.cs309.mmoserver.entity.characters.user.User;
import com.git.cs309.mmoserver.entity.characters.user.UserManager;
import com.git.cs309.mmoserver.entity.objects.GameObject;
import com.git.cs309.mmoserver.entity.objects.GameObjectFactory;
import com.git.cs309.mmoserver.items.GroundItemStack;
import com.git.cs309.mmoserver.items.ItemStack;
import com.git.cs309.mmoserver.packets.EntityUpdatePacket;
import com.git.cs309.mmoserver.packets.NewMapPacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.util.MathUtils;
import com.git.cs309.mmoserver.entity.characters.npc.NPCFactory;

public final class Map {
	private final int instanceNumber;
	private final MapDefinition definition;
	private volatile GroundItemStack[][] groundItems;
	private volatile Set<Entity> entitySet = new HashSet<>();
	private volatile Set<PlayerCharacter> playerSet = new HashSet<>();
	private final int[][] pathingMap;

	public Map(final MapDefinition definition, final int instanceNumber) {
		this.instanceNumber = instanceNumber;
		this.definition = definition;
		pathingMap = new int[definition.getWidth()][definition.getHeight()];
		groundItems = new GroundItemStack[definition.getWidth()][definition.getHeight()];
		setMapToNulls();
		setItemsToNulls();
		MapHandler.getInstance().addMap(this);
	}
	
	public int[][] getPathingMap() {
		int[][] copy = new int[pathingMap.length][pathingMap[0].length];
		for (int i = 0; i < pathingMap.length; i++) {
			System.arraycopy(pathingMap[i], 0, copy[i], 0, pathingMap[i].length);
		}
		return copy;
	}

	public boolean containsPoint(final int x, final int y) {
		return getXOrigin() + getWidth() > x && x >= getXOrigin() && getYOrigin() + getHeight() > y
				&& y >= getYOrigin();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Map)) {
			return false;
		}
		Map otherMap = (Map) other;
		return otherMap.instanceNumber == instanceNumber && otherMap.definition.equals(definition);
	}

	public Entity[] getEntities(final int x, final int y) {
		assert (containsPoint(x, y));
		List<Entity> entities = new ArrayList<>();
		for (Entity entity : entitySet) {
			if (entity.getX() == x && entity.getY() == y) {
				entities.add(entity);
			}
		}
		return entities.toArray(new Entity[entities.size()]);
	}
	
	public boolean walkable(final int x, final int y) {
		assert (containsPoint(x, y));
		return pathingMap[globalToLocalX(x)][globalToLocalY(y)] == PathFinder.EMPTY;
	}

	public Entity getEntity(final int uniqueId, final int x, final int y) {
		assert (containsPoint(x, y));
		for (Entity entity : entitySet) {
			if (entity.getX() == x && entity.getY() == y && entity.getUniqueID() == uniqueId) {
				return entity;
			}
		}
		throw new RuntimeException("No entity at position ("+x+", "+y+") with ID: "+uniqueId);
	}
	
	public Entity getEntity(final int x, final int y) {
		assert (containsPoint(x, y));
		for (Entity entity : entitySet) {
			if (entity.getX() == x && entity.getY() == y) {
				return entity;
			}
		}
		throw new RuntimeException("No entity at position ("+x+", "+y+")");
	}

	public int getHeight() {
		return definition.getHeight();
	}

	public final int getInstanceNumber() {
		return instanceNumber;
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

	public int getZ() {
		return definition.getZ();
	}

	public void moveEntity(final int uniqueId, final int oX, final int oY, final int dX, final int dY) {
		assert containsPoint(oX, oY) && containsPoint(dX, dY) && walkable(dX, dY);
		assert ((int) MathUtils.distance(oX, oY, dX, dY)) <= 1;
		Entity entity = getEntity(uniqueId, oX, oY);
		assert entity != null;
		sendPacketToPlayers(new EntityUpdatePacket(null, EntityUpdatePacket.MOVED, entity.getUniqueID(), dX, dY));
		if (!entity.canWalkThrough()) {
			pathingMap[globalToLocalX(dX)][globalToLocalY(dY)] = PathFinder.CANT_WALK;
			pathingMap[globalToLocalX(oX)][globalToLocalY(oY)] = PathFinder.EMPTY;
		}
	}
	
	public void putItemStack(final int x, final int y, final ItemStack items) {
		assert containsPoint(x, y) && walkable(x, y);
		GroundItemStack stack = getGroundItemStack(x, y);
		if (stack == null) {
			groundItems[x][y] = new GroundItemStack(x, y, this);
			groundItems[x][y].addItemStack(items);
		} else {
			stack.addItemStack(items);
		}
		itemStackChanged(x, y);
	}
	
	public ItemStack removeItemStack(final int x, final int y) {
		assert containsPoint(x, y) && walkable(x, y);
		GroundItemStack stack = getGroundItemStack(x, y);
		if (stack == null) {
			return null;
		} else {
			ItemStack item = stack.removeStack(0);
			itemStackChanged(x, y);
			return item;
		}
	}
	
	public ItemStack removeItemStack(final int x, final int y, final int index) {
		assert containsPoint(x, y) && walkable(x, y);
		GroundItemStack stack = getGroundItemStack(x, y);
		if (stack == null) {
			return null;
		} else {
			ItemStack item = stack.removeStack(index);
			itemStackChanged(x, y);
			return item;
		}
	}
	
	public void itemStackChanged(final int x, final int y) {
		GroundItemStack stack = getGroundItemStack(x, y);
		assert stack != null;
		sendPacketToPlayers(stack.getExtensivePacket());
	}
	
	public GroundItemStack getGroundItemStack(final int x, final int y) {
		assert containsPoint(x, y) && walkable(x, y);
		return groundItems[x][y];
	}

	public void putEntity(final int x, final int y, final Entity entity) {
		assert (containsPoint(x, y));
		assert entity != null && !entitySet.contains(entity);
		sendEntityToPlayers(entity);
		if (entity.getEntityType() == EntityType.PLAYER) {
			UserManager.getUserForUserID(((PlayerCharacter) entity).getUniqueID()).getConnection().addOutgoingPacket(new NewMapPacket(null, definition.getMapName()));
			sendEntitiesToPlayer((PlayerCharacter) entity);
			entitySet.add(entity);
			playerSet.add((PlayerCharacter) entity);
			return;
		}
		entitySet.add(entity);
		if (!entity.canWalkThrough())
			pathingMap[globalToLocalX(x)][globalToLocalY(y)] = PathFinder.CANT_WALK;
	}

	public void removeEntity(final int x, final int y, final Entity entity) {
		assert (containsPoint(x, y));
		assert entity != null;
		if (entity.getEntityType() == EntityType.PLAYER)
			playerSet.remove(entity);
		sendPacketToPlayers(new EntityUpdatePacket(null, EntityUpdatePacket.REMOVED, entity.getUniqueID(), x, y));
		entitySet.remove(entity);
		if (!entity.canWalkThrough() && getEntities(x, y).length == 0) {
			pathingMap[globalToLocalX(x)][globalToLocalY(y)] = PathFinder.EMPTY;
		}
		if (playerSet.size() == 0 && instanceNumber != Config.GLOBAL_INSTANCE) {
			cleanUp();
			MapHandler.getInstance().removeMap(this);
		}
	}
	
	private void cleanUp() {
		assert (playerSet.size() == 0);
		for (Entity e : entitySet) {
			e.cleanUp();
		}
		for (GroundItemStack[] array : groundItems) {
			for (GroundItemStack stack : array) {
				stack.cleanUp();
			}
		}
	}

	public void sendPacketToPlayers(Packet packet) {
		for (PlayerCharacter e : playerSet) {
			User user = UserManager.getUserForUserID(e.getUniqueID());
			if (user == null) {
				continue;
			}
			Connection userConnection = (Connection) user.getConnection();
			if (userConnection == null) {
				continue;
			}
			userConnection.addOutgoingPacket(packet);
		}
	}

	private void sendEntitiesToPlayer(PlayerCharacter player) {
		Connection userConnection = (Connection) UserManager.getUserForUserID(player.getUniqueID()).getConnection();
		assert userConnection != null;
		for (Entity e : entitySet) {
			if (e.getEntityType() == EntityType.OBJECT && ((GameObject)e).isServerOnly()) {
				continue;
			}
			userConnection.addOutgoingPacket(e.getExtensivePacket());
		}
	}

	private void sendEntityToPlayers(Entity entity) {
		sendPacketToPlayers(entity.getExtensivePacket());
	}

	private void setMapToNulls() {
		for (int i = 0; i < pathingMap.length; i++) {
			for (int j = 0; j < pathingMap[i].length; j++) {
				pathingMap[i][j] = -1;
			}
		}
	}
	
	private void setItemsToNulls() {
		for (int i = 0; i < groundItems.length; i++) {
			for (int j = 0; j < groundItems[i].length; j++) {
				groundItems[i][j] = null;
			}
		}
	}

	void loadSpawns() {
		for (Spawn spawn : definition.getSpawns()) {
			switch (spawn.getType()) {
			case Spawn.CHARACTER:
				NPCFactory.getInstance().createNPC(spawn.getName(), spawn.getX(), spawn.getY(), definition.getZ(),
						instanceNumber);
				break;
			case Spawn.OBJECT:
				GameObjectFactory.getInstance().createGameObject(spawn.getName(), spawn.getX(), spawn.getY(),
						definition.getZ(), instanceNumber);
				break;
			case Spawn.NULL:
				break;
			default:
				System.err.println("No case for spawn type when loading map spawns: " + spawn.getType());
				break;
			}
		}
	}
}
