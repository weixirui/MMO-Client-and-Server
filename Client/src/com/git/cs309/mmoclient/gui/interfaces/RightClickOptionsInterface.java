package com.git.cs309.mmoclient.gui.interfaces;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class RightClickOptionsInterface extends GameInterface {
	
	private final String[] options;

	public RightClickOptionsInterface(int positionX, int positionY, String ... options) {
		super("rightclickoptions", 200, 100, positionX, positionY);
		this.options = options;
	}
	
	@Override
	public void paint(Graphics g) {
		int width = 0;
		FontMetrics metrics = g.getFontMetrics();
		for (String option : options) {
			int optionWidth = metrics.stringWidth(option);
			if (optionWidth > width) {
				width = optionWidth;
			}
		}
		g.setColor(INTERFACE_BACKGROUND_COLOR);
		g.fillRect(0, 0, width + 10, metrics.getHeight() * options.length);
		int y = metrics.getAscent();
		g.setColor(Color.BLACK);
		for (String option : options) {
			g.drawString(option, 5, y);
			y += metrics.getHeight();
		}
	}

}
