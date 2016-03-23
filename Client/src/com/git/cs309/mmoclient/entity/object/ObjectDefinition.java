package com.git.cs309.mmoclient.entity.object;

public final class ObjectDefinition {
	private final int objectID;
	private final String objectName;

	public ObjectDefinition(final String objectName, final int objectID) {
		this.objectID = objectID;
		this.objectName = objectName;
	}

	public final int getObjectID() {
		return objectID;
	}

	public final String getObjectName() {
		return objectName;
	}
}
