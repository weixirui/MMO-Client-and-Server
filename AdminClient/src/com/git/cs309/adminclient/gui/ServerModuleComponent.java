package com.git.cs309.adminclient.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class ServerModuleComponent extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3027703864639362365L;
	private float drag = 1.0F;
	private final String name;

	public ServerModuleComponent(final String name) {
		this.name = name;
	}

	public String getModuleName() {
		return name;
	}

	@Override
	public void paint(Graphics g) {
		int red = (int) (drag * 0xFF);
		if (red > 0xFF) {
			red = 0xFF;
		}
		if (red < 0) {
			red = 0;
		}
		g.setColor(new Color(red, 0xFF - red, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		int x = (this.getWidth() - g.getFontMetrics().stringWidth(name)) / 2;
		int y = ((this.getHeight() - g.getFontMetrics().getHeight()) / 2) + g.getFontMetrics().getAscent();
		g.setColor(Color.BLACK);
		g.drawString(name, x, y);
	}

	public void setDrag(final float drag) {
		this.drag = drag;
	}
}
