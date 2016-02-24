package com.git.cs309.adminclient;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.git.cs309.adminclient.connection.Connection;
import com.git.cs309.adminclient.gui.LoginGUI;

public final class AdminClient {
	private static boolean debug = false;
	private static volatile Connection connection;

	public static Connection getConnection() {
		return connection;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void main(String[] args) {
		for (String arg : args) {
			switch (arg.toLowerCase()) {
			case "-d":
				debug = true;
				break;
			}
		}
		try {
			connection = new Connection(new Socket(debug ? "localhost" : "proj-309-21.cs.iastate.edu", 43594));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to connect to the server.");
			return;
		}
		LoginGUI.getSingleton().setVisible(true);
	}
}
