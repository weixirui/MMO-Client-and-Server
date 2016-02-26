package com.git.cs309.mmoserver.map;

import java.util.HashSet;
import java.util.Set;

import com.git.cs309.mmoserver.connection.Connection;
import com.git.cs309.mmoserver.entity.Entity;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.entity.characters.user.UserManager;
import com.git.cs309.mmoserver.entity.objects.GameObject;
import com.git.cs309.mmoserver.packets.EntityUpdatePacket;
import com.git.cs309.mmoserver.packets.ExtensiveCharacterPacket;
import com.git.cs309.mmoserver.packets.ExtensiveObjectPacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.entity.characters.Character;

public final class Map {
	private final int instanceNumber;
	private final int xOrigin; // (0, 0) on this graphs actual X value as far as whole map is concerned
	private final int yOrigin; // (0, 0) on this graphs actual Y value as far as whole map is concerned
	private final int z;
	private final int width;
	private final int height;
	private volatile Entity[][] entityMap;
	private volatile Set<Entity> entitySet = new HashSet<Entity>();

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Map)) {
			return false;
		}
		Map otherMap = (Map) other;
		return otherMap.instanceNumber == instanceNumber && otherMap.z == z && otherMap.xOrigin == xOrigin
				&& otherMap.yOrigin == yOrigin;
	}

	public Map(final int instanceNumber, final int xOrigin, final int yOrigin, final int z, final int width,
			final int height) {
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
				entityMap[i][j] = null;
			}
		}
	}

	public boolean containsPoint(final int x, final int y) {
		return xOrigin + width >= x && x >= xOrigin && yOrigin + height >= y && y >= yOrigin;
	}

	private void sendEntitiesToEntity(Entity entity) {
		if (entity.getEntityType() != EntityType.PLAYER) {
			return;
		}
		Connection userConnection = (Connection) UserManager.getUserForUserID(entity.getUniqueID()).getConnection();
		for (Entity e : entitySet) {
			switch (e.getEntityType()) {
			case OBJECT:
				GameObject object = (GameObject) e;
				userConnection.addOutgoingPacket(new ExtensiveObjectPacket(null, object.getUniqueID(),
						object.getStaticID(), object.getX(), object.getY(), object.getName()));
				break;
			case PLAYER:
				//TODO Implement
				break;
			case NPC:
				Character character = (Character) e;
				userConnection.addOutgoingPacket(new ExtensiveCharacterPacket(null, character.getUniqueID(),
						character.getStaticID(), character.getX(), character.getY(), character.getHealth(),
						character.getMaxHealth(), character.getLevel(), character.getName()));
				break;
			}
		}
	}

	public void sendPacketToPlayers(Packet packet) {
		for (Entity e : entitySet) {
			if (e.getEntityType() != EntityType.PLAYER) {
				continue;
			}
			Connection userConnection = (Connection) UserManager.getUserForUserID(e.getUniqueID()).getConnection();
			userConnection.addOutgoingPacket(packet);
		}
	}

	private void sendEntityToEntities(Entity entity) {
		switch (entity.getEntityType()) {
		case OBJECT:
			GameObject object = (GameObject) entity;
			sendPacketToPlayers(new ExtensiveObjectPacket(null, object.getUniqueID(), object.getStaticID(),
					object.getX(), object.getY(), object.getName()));
			break;
		case PLAYER:
			//TODO Implement
			break;
		case NPC:
			Character character = (Character) entity;
			sendPacketToPlayers(new ExtensiveCharacterPacket(null, character.getUniqueID(), character.getStaticID(),
					character.getX(), character.getY(), character.getHealth(), character.getMaxHealth(),
					character.getLevel(), character.getName()));
			break;
		}
	}

	public void moveEntity(final int oX, final int oY, final int dX, final int dY) {
		sendPacketToPlayers(
				new EntityUpdatePacket(null, EntityUpdatePacket.MOVED, entityMap[oX][oY].getUniqueID(), dX, dY));
		entityMap[dX][dY] = entityMap[oX][oY];
		entityMap[oX][oY] = null;
	}

	public void setEntityOnGlobal(final int x, final int y, final Entity entity) {
		if (entity != null && !entitySet.contains(entity)) {
			//TODO send map packet
			sendEntitiesToEntity(entity);
			sendEntityToEntities(entity);
		} else if (getEntityOnGlobal(x, y) != null && entity == null) { //Leaving map
			sendPacketToPlayers(new EntityUpdatePacket(null, EntityUpdatePacket.REMOVED,
					getEntityOnGlobal(x, y).getUniqueID(), x, y));
		}
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
