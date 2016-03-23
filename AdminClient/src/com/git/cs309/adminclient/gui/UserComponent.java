package com.git.cs309.adminclient.gui;

import java.awt.Component;
import java.awt.Graphics;

public class UserComponent extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4109077034204819173L;
	private final int userID;
	private final String userName;
	private final String permissionName;

	public UserComponent(final int userID, final String userName, final String permissionName) {
		this.userID = userID;
		this.userName = userName;
		this.permissionName = permissionName;
	}

	@Override
	public void paint(Graphics g) {
		String message = "Name: " + userName + ", ID: " + userID + ", permissions: " + permissionName;
		int x = (this.getWidth() - g.getFontMetrics().stringWidth(message)) / 2;
		int y = ((this.getHeight() - g.getFontMetrics().getHeight()) / 2) + g.getFontMetrics().getAscent();
		g.drawString(message, x, y);
	}
}
