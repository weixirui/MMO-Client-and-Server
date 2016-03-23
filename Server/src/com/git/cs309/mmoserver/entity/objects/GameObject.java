package com.git.cs309.mmoserver.entity.objects;

import com.git.cs309.mmoserver.entity.Entity;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.packets.ExtensiveObjectPacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.util.ClosedIDSystem;

public class GameObject extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3289635025699381316L;
	private transient final ObjectDefinition definition;

	public GameObject(ObjectDefinition definition, int x, int y, int z, int instanceNumber) {
		super(x, y, z, ClosedIDSystem.getTag(), z, definition.getObjectName());
		this.instanceNumber = instanceNumber;
		this.definition = definition;
		MapHandler.getInstance().putEntityAtPosition(instanceNumber, x, y, z, this);
	}

	@Override
	public boolean canWalkThrough() {
		return definition.getWalkable();
	}

	public ObjectDefinition getDefinition() {
		return definition;
	}
	
	public boolean isServerOnly() {
		return definition.isServerOnly();
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.OBJECT;
	}

	@Override
	public Packet getExtensivePacket() {
		return new ExtensiveObjectPacket(null, getUniqueID(), definition.getObjectID(), x, y,
				definition.getObjectName());
	}

}
