package com.git.cs309.mmoserver.entity;

import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

public abstract class Entity {
	protected transient IDTag idTag; // Unique identifier
	protected volatile int x = 0, y = 0, z = 0; // Coordinates
	protected int entityID = -1;
	protected transient int instanceNumber = 0;
	protected String name = "Null";
	protected volatile boolean needsDisposal = false;
	protected volatile boolean onMap = false;

	public Entity() {
		instanceNumber = 0;
	}

	public Entity(final int x, final int y, final int z, final IDTag idTag, final int entityID, final String name) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.idTag = idTag;
		this.entityID = entityID;
		this.name = name;
		instanceNumber = 0;
	}

	public abstract boolean canWalkThrough();

	public final void cleanUp() {
		needsDisposal = true;
		if (idTag != null) {
			idTag.returnTag();
		}
		idTag = null;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Entity)) {
			return false;
		}
		Entity entity = (Entity) other;
		return entity.x == x && entity.y == y && entity.z == z && entity.idTag.equals(idTag)
				&& name.equals(entity.name);
	}

	public abstract EntityType getEntityType();

	public final int getInstanceNumber() {
		return instanceNumber;
	}

	public final String getName() {
		return name;
	}

	public final int getStaticID() {
		return entityID;
	}

	public final int getUniqueID() {
		return idTag.getID();
	}

	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	public final int getZ() {
		return z;
	}

	public final boolean needsDisposal() {
		return needsDisposal;
	}

	public final void setInstanceNumber(int instanceNumber) {
		this.instanceNumber = instanceNumber;
	}

	public final void setPosition(final int x, final int y, final int z) {
		MapHandler.moveEntity(instanceNumber, this.x, this.y, this.z, instanceNumber, x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public final void setPosition(final int instanceNumber, final int x, final int y, final int z) {
		MapHandler.moveEntity(this.instanceNumber, this.x, this.y, this.z, instanceNumber, x, y, z);
		this.instanceNumber = instanceNumber;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	protected final void setIDTag(final IDTag idTag) {
		this.idTag = idTag;
	}
}
