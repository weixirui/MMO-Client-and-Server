package com.git.cs309.mmoclient.gui;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class CharacterSelectionGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7062272668164270613L;
	
	private static final CharacterSelectionGUI SINGLETON = new CharacterSelectionGUI();
	
	public static final CharacterSelectionGUI getSingleton() {
		return SINGLETON;
	}
	
	private CharacterSelectionGUI() {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
