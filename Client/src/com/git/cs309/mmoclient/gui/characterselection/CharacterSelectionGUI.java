package com.git.cs309.mmoclient.gui.characterselection;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.git.cs309.mmoserver.packets.CharacterSelectionDataPacket;

public class CharacterSelectionGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7062272668164270613L;
	
	private static final CharacterSelectionGUI SINGLETON = new CharacterSelectionGUI();
	
	public static final CharacterSelectionGUI getSingleton() {
		return SINGLETON;
	}
	
	private final SelectionCharacterComponent[] components = new SelectionCharacterComponent[5];
	
	private CharacterSelectionGUI() {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(0, 5));
		for (int i = 0; i < 5; i++) {
			components[i] = new SelectionCharacterComponent(i);
			add(components[i]);
		}
	}
	
	public void updateComponents(CharacterSelectionDataPacket packet) {
		components[packet.getIndex()].updateSelectionCharacter(packet);
		this.repaint();
	}

}
