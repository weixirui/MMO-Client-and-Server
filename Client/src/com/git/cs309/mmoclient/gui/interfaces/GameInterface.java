package com.git.cs309.mmoclient.gui.interfaces;

import java.awt.Color;

import javax.swing.JPanel;

public abstract class GameInterface extends JPanel {
	
	public static final Color INTERFACE_BACKGROUND_COLOR = new Color(153, 102, 51);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6486724969870565952L;
	
	public GameInterface(String name, int width, int height, int positionX, int positionY) {
		this.setName(name);
		this.setLocation(positionX, positionY);
		this.setSize(width, height);
		this.setBackground(INTERFACE_BACKGROUND_COLOR);
	}
	
	public GameInterface(String name, int positionX, int positionY) {
		this.setName(name);
		this.setLocation(positionX, positionY);
		this.setBackground(INTERFACE_BACKGROUND_COLOR);
	}
}
