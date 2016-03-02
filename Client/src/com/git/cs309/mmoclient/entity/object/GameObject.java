package com.git.cs309.mmoclient.entity.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoclient.entity.Entity;
import com.git.cs309.mmoclient.entity.EntityType;
import com.git.cs309.mmoserver.packets.ExtensiveObjectPacket;

public class GameObject extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 778392789211407690L;
	private transient final ObjectDefinition definition;

	public GameObject(ExtensiveObjectPacket packet, ObjectDefinition definition) {
		super(packet.getX(), packet.getY(), packet.getUniqueID(), definition.getObjectID(), definition.getObjectName());
		this.definition = definition;
	}

	public ObjectDefinition getDefinition() {
		return definition;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.OBJECT;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(getPaintX(), getPaintY(), Config.DEFAULT_SPRITE_WIDTH, Config.DEFAULT_SPRITE_HEIGHT);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 18.0f));
		g.drawString(""+getName().charAt(0), getPaintX() + (Config.DEFAULT_SPRITE_WIDTH / 2) - 5, getPaintY() + (Config.DEFAULT_SPRITE_HEIGHT / 2) + 5);
	}

}
