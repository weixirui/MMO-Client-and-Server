package com.git.cs309.mmoclient.entity.object;

import com.git.cs309.mmoclient.entity.Entity;
import com.git.cs309.mmoclient.entity.EntityType;

public class GameObject extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 778392789211407690L;
	private transient final ObjectDefinition definition;

	public GameObject(ObjectDefinition definition, int x, int y, int uniqueId) {
		super(x, y, uniqueId, definition.getObjectID(), definition.getObjectName());
		this.definition = definition;
	}

	public ObjectDefinition getDefinition() {
		return definition;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.OBJECT;
	}

}
