package com.git.cs309.mmoclient.gui;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6023633314141474861L;
	
	private static final GameGUI SINGLETON = new GameGUI();
	
	private GameGUI() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static GameGUI getSingleton() {
		return SINGLETON;
	}
	
	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
	}
}
