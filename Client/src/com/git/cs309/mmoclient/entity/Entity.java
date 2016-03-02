package com.git.cs309.mmoclient.entity;

import java.awt.Component;
import java.awt.Graphics;

import com.git.cs309.mmoclient.Client;
import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoclient.gui.game.ViewPanel;

public abstract class Entity extends Component {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4656689876001467423L;
	protected volatile int x = 0, y = 0; // Coordinates
	protected int entityID = -1;
	protected final int uniqueId;
	protected String name = "Null";

	public Entity(final int x, final int y, final int uniqueId, final int entityID, final String name) {
		this.x = x;
		this.y = y;
		this.entityID = entityID;
		this.uniqueId = uniqueId;
		this.name = name;
		ViewPanel.getInstance().repaint();
	}
	
	@Override
	public abstract void paint(Graphics g);

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Entity)) {
			return false;
		}
		Entity entity = (Entity) other;
		return entity.x == x && entity.y == y && entity.uniqueId == uniqueId && name.equals(entity.name)
				&& getEntityType() == entity.getEntityType();
	}

	public abstract EntityType getEntityType();

	public final String getName() {
		return name;
	}

	public final int getStaticID() {
		return entityID;
	}

	public final int getUniqueID() {
		return uniqueId;
	}
	
	public int getPaintX() {
		return ((x - Client.getSelf().getX()) * Config.DEFAULT_SPRITE_WIDTH) + (ViewPanel.getInstance().getWidth() / 2) - (Config.DEFAULT_SPRITE_WIDTH / 2);
	}
	
	public int getPaintY() {
		return ((y - Client.getSelf().getY()) * Config.DEFAULT_SPRITE_HEIGHT) + (ViewPanel.getInstance().getHeight() / 2) - (Config.DEFAULT_SPRITE_HEIGHT / 2);
	}

	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	public final void setPosition(final int x, final int y) {
		//TODO handle walking
		this.x = x;
		this.y = y;
		ViewPanel.getInstance().repaint();
	}
}
