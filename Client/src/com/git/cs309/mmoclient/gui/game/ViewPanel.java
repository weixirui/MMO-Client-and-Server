package com.git.cs309.mmoclient.gui.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.git.cs309.mmoclient.Client;

public class ViewPanel extends JPanel {
	public static final Color WATER_COLOR = new Color(40, 40, 240);
	
	private static final ViewPanel INSTANCE = new ViewPanel();
	
	public static final ViewPanel getInstance() {
		return INSTANCE;
	}
	
	private ViewPanel() {
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7651841779875266840L;
	
	@Override
	public void paint(Graphics g) {
		if (Client.getSelf() == null || Client.getMap() == null)
			return;
		g.setColor(WATER_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		Client.getMap().paint(g);
		Client.getSelf().paint(g);
	}

}
