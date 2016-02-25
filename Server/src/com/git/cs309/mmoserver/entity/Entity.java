package com.git.cs309.mmoserver.entity;

import com.git.cs309.mmoserver.map.MapHandler;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

public abstract class Entity {
	protected transient IDTag idTag; // Unique identifier
	protected volatile int x = 0, y = 0, z  = 0; // Coordinates
	protected int entityID = -1;
	protected transient int instanceNumber = 0;
	protected String name = "Null";
	protected volatile boolean needsDisposal = false;
	
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
	
	public final boolean needsDisposal() {
		return needsDisposal;
	}
	
	public abstract boolean canWalkThrough();
	
	public final int getUniqueID() {
		return idTag.getID();
	}
	
	public final int getStaticID() {
		return entityID;
	}
	
	public final String getName() {
		return name;
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
	
	protected final void setIDTag(final IDTag idTag) {
		this.idTag = idTag;
	}
	
	public final void cleanUp() {
		needsDisposal = true;
		if (idTag != null) {
			idTag.returnTag();
		}
		idTag = null;
	}
	
	public final int getInstanceNumber() {
		return instanceNumber;
	}
	
	public final void setInstanceNumber(int instanceNumber) {
		this.instanceNumber = instanceNumber;
	}
	
	public final void setPosition(final int x, final int y, final int z) {
		MapHandler.setEntityAtPosition(this.x, this.y, this.z, null);
		this.x = x;
		this.y = y;
		this.z = z;
		MapHandler.setEntityAtPosition(x, y, z, this);
	}
	
	public abstract EntityType getEntityType();
}
