package com.git.cs309.mmoserver.entity.objects;

public final class ObjectDefinition {
	private final int objectID;
	private final String objectName;
	private final boolean walkable;

	public ObjectDefinition(final int objectID, final String objectName, final boolean walkable) {
		this.objectID = objectID;
		this.objectName = objectName;
		this.walkable = walkable;
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
}
