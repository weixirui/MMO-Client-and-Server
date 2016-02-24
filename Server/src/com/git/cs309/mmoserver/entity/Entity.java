package com.git.cs309.mmoserver.entity;

import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

public abstract class Entity {
	protected transient IDTag idTag; // Unique identifier
	protected volatile int x, y; // Coordinates
	protected int entityID = -1;
	protected String name = "Null";
	
	public Entity() {
		//For deserialization waaaay down the chain
	}
	
	public Entity(final int x, final int y, final IDTag idTag, final int entityID, final String name) {
		this.x = x;
		this.y = y;
		this.idTag = idTag;
		this.entityID = entityID;
		this.name = name;
	}
	
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
	
	public void setIDTag(final IDTag idTag) {
		this.idTag = idTag;
	}
	
	public void cleanUp() {
		idTag.returnTag();
	}
	
	public void setPosition(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
}
