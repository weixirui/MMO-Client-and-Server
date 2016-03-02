package com.git.cs309.mmoclient.gui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.git.cs309.mmoclient.Client;
import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoclient.entity.Entity;
import com.git.cs309.mmoclient.entity.character.Character;
import com.git.cs309.mmoclient.entity.EntityType;
import com.git.cs309.mmoserver.packets.MovePacket;

public class ViewPanel extends JPanel {
	public static final Color WATER_COLOR = new Color(40, 40, 240);
	
	private static final ViewPanel INSTANCE = new ViewPanel();
	
	public static final ViewPanel getInstance() {
		return INSTANCE;
	}
	
	private ViewPanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int gameX = Client.mouseXToGameX(e.getX());
				int gameY = Client.mouseYToGameY(e.getY());
				Client.lastClickX = gameX;
				Client.lastClickY = gameY;
				if (!Client.getMap().containsPoint(gameX, gameY)) {
					return;
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					//TODO Create popup menu with names which can be clicked and such
					for (Entity entity : Client.getMap().getEntities(gameX, gameY)) {
						if (entity.getEntityType() == EntityType.NPC || entity.getEntityType() == EntityType.PLAYER) {
							Character c = (Character) entity;
							System.out.println(c.getName()+" ("+c.getLevel()+")");
						} else {
							System.out.println(entity.getName());
						}
					}
				} else {
					Client.getConnection().addOutgoingPacket(new MovePacket(null, gameX, gameY));
				}
				ViewPanel.this.repaint();
			}
		});
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7651841779875266840L;
	
	@Override
	public void paint(Graphics g) {
	    Image offscreenImage = createImage(getWidth(), getHeight());
	    Graphics offscreenGraphics = offscreenImage.getGraphics();
		if (Client.getSelf() == null || Client.getMap() == null)
			return;
		offscreenGraphics.setColor(WATER_COLOR);
		offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
		Client.getMap().paint(offscreenGraphics);
		Client.getSelf().paint(offscreenGraphics);
		offscreenGraphics.setColor(Color.RED);
		offscreenGraphics.drawRect(((Client.lastClickX - Client.getSelf().getX()) * Config.DEFAULT_SPRITE_WIDTH) + (ViewPanel.getInstance().getWidth() / 2) - (Config.DEFAULT_SPRITE_WIDTH / 2), ((Client.lastClickY - Client.getSelf().getY()) * Config.DEFAULT_SPRITE_HEIGHT) + (ViewPanel.getInstance().getHeight() / 2) - (Config.DEFAULT_SPRITE_HEIGHT / 2), Config.DEFAULT_SPRITE_WIDTH, Config.DEFAULT_SPRITE_HEIGHT);
		g.drawImage(offscreenImage, 0, 0, null);
	}

}
