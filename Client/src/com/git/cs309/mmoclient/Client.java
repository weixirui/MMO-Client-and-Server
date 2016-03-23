package com.git.cs309.mmoclient;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.git.cs309.mmoclient.connection.Connection;
import com.git.cs309.mmoclient.entity.character.player.Self;
import com.git.cs309.mmoclient.graphics.SpriteDatabase;
import com.git.cs309.mmoclient.gui.game.ViewPanel;
import com.git.cs309.mmoclient.gui.login.LoginGUI;
import com.git.cs309.mmoclient.map.Map;

public final class Client {
	private static volatile Connection connection;
	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static boolean debug = false;
	
	private static int selfId = -1;
	private static Map currentMap = null;
	private static Self self = null;
	
	public static void main(String[] args) {
		for (String arg : args) {
			switch (arg.toLowerCase()) {
			case "-d":
				debug = true;
				break;
			}
		}
		SpriteDatabase.getInstance();
		try {
			connection = new Connection(new Socket(debug ? "localhost" : "proj-309-21.cs.iastate.edu", 43594));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to connect to the server.");
			return;
		}
		LoginGUI.getSingleton().setVisible(true);
	}
	
	public static void setSelfId(int selfId) {
		Client.selfId = selfId;
	}
	
	public static int mouseXToGameX(int x) {
		int x1 = x - (ViewPanel.getInstance().getWidth() / 2);
		if (x1 < 0) {
			x1 -= Config.DEFAULT_SPRITE_WIDTH / 2;
		} else {
			x1 += Config.DEFAULT_SPRITE_WIDTH /2;
		}
		return ((x1) / Config.DEFAULT_SPRITE_WIDTH) + Client.getSelf().getX();
	}
	
	public static int mouseYToGameY(int y) {
		int y1 = y- (ViewPanel.getInstance().getHeight() / 2);
		if (y1 < 0) {
			y1 -= Config.DEFAULT_SPRITE_HEIGHT / 2;
		} else {
			y1 += Config.DEFAULT_SPRITE_HEIGHT / 2;
		}
		return ((y1) / Config.DEFAULT_SPRITE_HEIGHT) + Client.getSelf().getY();
	}
	
	public static int getSelfId() {
		return selfId;
	}
	
	public static Self getSelf() {
		return self;
	}
	
	public static void setSelf(Self self) {
		Client.self = self;
	}
	
	public static void setMap(Map map) {
		Client.currentMap = map;
	}
	
	public static Map getMap() {
		return currentMap;
	}
	
	public static boolean isDebug() {
		return debug;
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static Dimension getScreenSize() {
		return SCREEN_SIZE;
	}
}
