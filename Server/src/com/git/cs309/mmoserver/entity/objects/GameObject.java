package com.git.cs309.mmoserver.entity.objects;

import com.git.cs309.mmoserver.entity.Entity;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.util.ClosedIDSystem;

public class GameObject extends Entity {
	private final ObjectDefinition definition;

	public GameObject(int instanceNumber, int x, int y, int z, ObjectDefinition definition) {
		super(x, y, z, ClosedIDSystem.getTag(), z, definition.getObjectName());
		this.instanceNumber = instanceNumber;
		this.definition = definition;
		MapHandler.setEntityAtPosition(instanceNumber, x, y, z, this);
	}

	public GameObject(int x, int y, int z, ObjectDefinition definition) {
		super(x, y, z, ClosedIDSystem.getTag(), z, definition.getObjectName());
		this.definition = definition;
		MapHandler.setEntityAtPosition(instanceNumber, x, y, z, this);
	}

	@Override
	public boolean canWalkThrough() {
		return definition.getWalkable();
	}

	public ObjectDefinition getDefinition() {
		return definition;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.OBJECT;
	}

}
