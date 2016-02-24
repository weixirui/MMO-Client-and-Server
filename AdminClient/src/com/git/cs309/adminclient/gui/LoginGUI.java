package com.git.cs309.adminclient.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.git.cs309.adminclient.AdminClient;
import com.git.cs309.mmoserver.packets.LoginPacket;

public class LoginGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1830528029732548694L;
	private static final LoginGUI SINGLETON = new LoginGUI();

	public static LoginGUI getSingleton() {
		return SINGLETON;
	}

	private LoginGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		JButton loginButton = new JButton("Login");
		this.setLayout(new GridLayout(3, 1));
		usernameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				passwordField.grabFocus();
			}

		});
		ActionListener loginListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AdminClient.getConnection().addOutgoingPacket(
						new LoginPacket(null, usernameField.getText(), String.valueOf(passwordField.getPassword())));
			}

		};
		passwordField.addActionListener(loginListener);
		loginButton.addActionListener(loginListener);
		add(usernameField);
		add(passwordField);
		add(loginButton);
		pack();
	}
}
