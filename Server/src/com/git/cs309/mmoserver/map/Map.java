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
import com.git.cs309.mmoserver.entity.characters.user.UserManager;
import com.git.cs309.mmoserver.entity.objects.GameObjectFactory;
import com.git.cs309.mmoserver.packets.EntityUpdatePacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.entity.characters.npc.NPCFactory;

public final class Map {
	private final int instanceNumber;
	private final MapDefinition definition;
	private volatile Entity[][] entityMap;
	private volatile Set<Entity> entitySet = new HashSet<>();
	private volatile Set<PlayerCharacter> playerSet = new HashSet<>();

	public Map(final MapDefinition definition, final int instanceNumber) {
		this.instanceNumber = instanceNumber;
		this.definition = definition;
		entityMap = new Entity[definition.getWidth()][definition.getHeight()];
		setMapToNulls();
		MapHandler.getInstance().addMap(this);
	}

	public boolean containsPoint(final int x, final int y) {
		return getXOrigin() + getWidth() >= x && x >= getXOrigin() && getYOrigin() + getHeight() >= y
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
		Entity e = entityMap[x - getXOrigin()][y - getYOrigin()];
		if (e != null) {
			return new Entity[] { e };
		}
		List<Entity> entities = new ArrayList<>();
		for (Entity entity : entitySet) {
			if (entity.getX() == x && entity.getY() == y) {
				entities.add(entity);
			}
		}
		return entities.toArray(new Entity[entities.size()]);
	}

	public Entity getEntity(final int x, final int y) {
		assert (containsPoint(x, y));
		Entity e = entityMap[x - getXOrigin()][y - getYOrigin()];
		if (e != null) {
			return e;
		}
		for (Entity entity : entitySet) {
			if (entity.getX() == x && entity.getY() == y) {
				return entity;
			}
		}
		return null;
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

	public final int getXOrigin() {
		return definition.getxOrigin();
	}

	public final int getYOrigin() {
		return definition.getyOrigin();
	}

	public int getZ() {
		return definition.getZ();
	}

	public void moveEntity(final int oX, final int oY, final int dX, final int dY) {
		assert containsPoint(oX, oY) && containsPoint(dX, dY);
		Entity entity = getEntity(oX, oY);
		assert entity != null;
		sendPacketToPlayers(new EntityUpdatePacket(null, EntityUpdatePacket.MOVED, entity.getUniqueID(), dX, dY));
		if (entity.getEntityType() != EntityType.PLAYER) {
			entityMap[dX][dY] = entityMap[oX][oY];
			entityMap[oX][oY] = null;
		}
	}

	public void putEntity(final int x, final int y, final Entity entity) {
		assert (containsPoint(x, y));
		assert entity != null && !entitySet.contains(entity);
		//TODO send actual map packet
		sendEntityToPlayers(entity);
		if (entity.getEntityType() == EntityType.PLAYER) {
			sendEntitiesToPlayer((PlayerCharacter) entity);
			entitySet.add(entity);
			playerSet.add((PlayerCharacter) entity);
			return;
		}
		entitySet.add(entity);
		entityMap[x - getXOrigin()][y - getYOrigin()] = entity;
	}

	public void removeEntity(final int x, final int y) {
		assert (containsPoint(x, y));
		Entity entity = getEntity(x, y);
		assert entity != null;
		if (entity.getEntityType() == EntityType.PLAYER)
			playerSet.remove(entity);
		sendPacketToPlayers(new EntityUpdatePacket(null, EntityUpdatePacket.REMOVED, entity.getUniqueID(), x, y));
		entitySet.remove(entity);
		if (entity.getEntityType() != EntityType.PLAYER)
			entityMap[x - getXOrigin()][y - getYOrigin()] = null;
		if (playerSet.size() == 0 && instanceNumber != Config.GLOBAL_INSTANCE) {
			MapHandler.getInstance().removeMap(this);
		}
	}

	public void sendPacketToPlayers(Packet packet) {
		for (PlayerCharacter e : playerSet) {
			Connection userConnection = (Connection) UserManager.getUserForUserID(e.getUniqueID()).getConnection();
			userConnection.addOutgoingPacket(packet);
		}
	}

	private void sendEntitiesToPlayer(PlayerCharacter player) {
		Connection userConnection = (Connection) UserManager.getUserForUserID(player.getUniqueID()).getConnection();
		assert userConnection != null;
		for (Entity e : entitySet) {
			userConnection.addOutgoingPacket(e.getExtensivePacket());
		}
	}

	private void sendEntityToPlayers(Entity entity) {
		sendPacketToPlayers(entity.getExtensivePacket());
	}

	private void setMapToNulls() {
		for (int i = 0; i < entityMap.length; i++) {
			for (int j = 0; j < entityMap[i].length; j++) {
				entityMap[i][j] = null;
			}
		}
	}

	protected void cleanUp() {
		assert (playerSet.size() == 0);
		for (Entity e : entitySet) {
			e.cleanUp();
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
