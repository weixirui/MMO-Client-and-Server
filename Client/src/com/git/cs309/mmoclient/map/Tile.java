package com.git.cs309.mmoclient.map;

import java.awt.Color;
import java.awt.Graphics;

import com.git.cs309.mmoclient.Client;
import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoclient.graphics.Sprite;
import com.git.cs309.mmoclient.graphics.SpriteDatabase;
import com.git.cs309.mmoclient.gui.game.ViewPanel;

public final class Tile {
	private final int x, y;
	private final String spriteName;

	public Tile(final int x, final int y, final String spriteName) {
		this.x = x;
		this.y = y;
		this.spriteName = spriteName;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	public int getPaintX() {
		return ((x - Client.getSelf().getX()) * Config.DEFAULT_SPRITE_WIDTH) + (ViewPanel.getInstance().getWidth() / 2)
				- (Config.DEFAULT_SPRITE_WIDTH / 2);
	}

	public int getPaintY() {
		return ((y - Client.getSelf().getY() - 1) * Config.DEFAULT_SPRITE_HEIGHT)
				+ (ViewPanel.getInstance().getHeight() / 2) - (Config.DEFAULT_SPRITE_HEIGHT / 2);
	}

	/**
	 * @return the spriteName
	 */
	public String getSpriteName() {
		return spriteName;
	}

	public void paint(Graphics g) {
		Sprite sprite = SpriteDatabase.getInstance().getSprite(spriteName);
		if (sprite != null) {
			g.drawImage(sprite.getImage(), getPaintX(), getPaintY(), Config.DEFAULT_SPRITE_WIDTH, Config.DEFAULT_SPRITE_HEIGHT, null);
		} else {
			switch (getSpriteName()) {
			case "grass":
				g.setColor(new Color(0x00, 0x7F, 0x00));
				break;
			case "road":
				g.setColor(new Color(255, 230, 204));
				break;
			}
			g.fillRect(getPaintX(), getPaintY(), Config.DEFAULT_SPRITE_WIDTH, Config.DEFAULT_SPRITE_HEIGHT);
		}
	}
}
