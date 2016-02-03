package com.git.cs309.mmoclient.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;

import com.engine.graphics.Engine;

public class GameGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6023633314141474861L;

	private static final GameGUI SINGLETON = new GameGUI();
	private static final Thread repainter = new Thread() {
		@Override
		public void run() {
			while (SINGLETON.isVisible()) {
				SINGLETON.repaint();
			}
		}
	};

	public static GameGUI getSingleton() {
		return SINGLETON;
	}

	private GameGUI() {
		setLayout(new GridLayout(2, 1));
		add(Engine.getSingleton());
		setSize(4000, 3000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
		repainter.start();
	}
}
