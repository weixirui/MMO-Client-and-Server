package com.git.cs309.mmoclient.gui.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.git.cs309.mmoclient.Client;
import com.git.cs309.mmoclient.gui.game.ViewPanel;
import com.git.cs309.mmoserver.packets.MessagePacket;

public final class ChatBox extends JPanel {
	
	private static final ChatBox INSTANCE = new ChatBox();
	
	public static final ChatBox getInstance() {
		return INSTANCE;
	}
	
	private final JTextArea chatArea = new JTextArea();
	private final JTextField messageArea = new JTextField();
	
	public void addMessage(String message) {
		chatArea.append(message + "\n");
		chatArea.setCaretPosition(chatArea.getText().length());
	}
	
	private ChatBox() {
		Color background = new Color(153, 102, 51, 0x7F);
		this.setBackground(background);
		this.setSize(500, 200);
		setLayout(new BorderLayout());
		chatArea.setEditable(false);
		chatArea.setBackground(background);
		chatArea.setForeground(Color.YELLOW);
		messageArea.setBackground(background);
		messageArea.setForeground(Color.YELLOW);
		add(chatArea, BorderLayout.CENTER);
		add(messageArea, BorderLayout.SOUTH);
		messageArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = messageArea.getText();
				messageArea.setText("");
				if (message.length() > 0) {
					if (message.equalsIgnoreCase("cls")) {
						chatArea.setText("");
						chatArea.setCaretPosition(0);
					} else {
						Client.getConnection()
								.addOutgoingPacket(new MessagePacket(null, MessagePacket.UNKNOWN, message));
					}
				}
				ViewPanel.getInstance().repaint();
			}

		});
	}
}
