package com.git.cs309.mmoserver.entity.objects;

public final class ObjectDefinition {
	private final int objectID;
	private final String objectName;
	private final boolean walkable;
	private final boolean serverOnly;

	public ObjectDefinition(final String objectName, final int objectID, final boolean walkable, final boolean serverOnly) {
		this.objectID = objectID;
		this.objectName = objectName;
		this.walkable = walkable;
		this.serverOnly = serverOnly;
	}

	public final int getObjectID() {
		return objectID;
	}

	public final String getObjectName() {
		return objectName;
	}

	public boolean getWalkable() {
		return walkable;
	}

	/**
	 * @return the serverOnly
	 */
	public boolean isServerOnly() {
		return serverOnly;
	}
}
