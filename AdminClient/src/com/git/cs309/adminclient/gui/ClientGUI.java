package com.git.cs309.adminclient.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextPane;

import com.git.cs309.adminclient.AdminClient;
import com.git.cs309.mmoserver.packets.AdminCommandPacket;
import com.git.cs309.mmoserver.packets.MessagePacket;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.FlowLayout;

public class ClientGUI extends JFrame {

	private final class ListModel<T> extends AbstractListModel<T> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final List<T> tList = new ArrayList<>();

		public void add(T t) {
			tList.add(t);
			fireContentsChanged(this, 0, tList.size());
		}

		@Override
		public T getElementAt(int index) {
			return tList.get(index);
		}

		@Override
		public int getSize() {
			return tList.size();
		}

	}

	private static final ClientGUI SINGLETON = new ClientGUI();

	private static JTextArea chatBox;
	public static void addMessage(String message) {
		chatBox.append(message + "\n");
		chatBox.setCaretPosition(chatBox.getText().length());
	}
	public static ClientGUI getSingleton() {
		return SINGLETON;
	}
	private List<ServerModuleComponent> serverModuleList = new ArrayList<>();

	private ListModel<UserComponent> userModel = new ListModel<>();

	private JTextField chatField;

	private ClientGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		serverModuleList.add(new ServerModuleComponent("ConnectionManager"));
		serverModuleList.add(new ServerModuleComponent("CharacterManager"));
		serverModuleList.add(new ServerModuleComponent("CycleProcessManager"));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel serverPanel = new JPanel();
		tabbedPane.addTab("Server", null, serverPanel, null);
		serverPanel.setLayout(new BorderLayout(0, 0));

		JPanel serverStatusPanel = new JPanel();
		serverPanel.add(serverStatusPanel, BorderLayout.CENTER);
		serverStatusPanel.setLayout(new GridLayout(4, 0, 5, 0));
		for (ServerModuleComponent component : serverModuleList) {
			serverStatusPanel.add(component);
		}
		JButton restartServerButton = new JButton("Restart Server");
		serverStatusPanel.add(restartServerButton);
		restartServerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to restart the server? This can cause the server to shutdown entirely.") == JOptionPane.YES_OPTION) {
					AdminClient.getConnection()
							.addOutgoingPacket(new AdminCommandPacket(null, AdminCommandPacket.RESTART_SERVER, 0));
				}
			}

		});

		JPanel usersPanel = new JPanel();
		tabbedPane.addTab("Users", null, usersPanel, null);
		usersPanel.setLayout(new BorderLayout(0, 0));

		JList<UserComponent> userList = new JList<>(userModel);
		usersPanel.add(userList, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		usersPanel.add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 4, 4));

		JComboBox<String> durationBox = new JComboBox<>();
		durationBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "1 Day", "2 Days", "3 Days", "4 Days", "7 Days", "2 Weeks", "1 Year", "Life" }));
		panel.add(durationBox);

		JButton banButton = new JButton("Ban User");
		panel.add(banButton);

		JButton ipBanUserButton = new JButton("IP Ban User");
		panel.add(ipBanUserButton);

		JButton muteButton = new JButton("Mute User");
		panel.add(muteButton);

		JButton ipMuteUserButton = new JButton("IP Mute User");
		panel.add(ipMuteUserButton);

		JComboBox<String> rightsBox = new JComboBox<>();
		rightsBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Player", "Moderator", "Administrator" }));
		panel.add(rightsBox);

		JButton promotebutton = new JButton("Promote User");
		panel.add(promotebutton);

		JPanel chatPanel = new JPanel();
		tabbedPane.addTab("Chat", null, chatPanel, null);
		chatPanel.setLayout(new BorderLayout(0, 0));

		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatPanel.add(chatBox, BorderLayout.CENTER);

		chatField = new JTextField();
		chatField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = chatField.getText();
				chatField.setText("");
				if (message.length() > 0) {
					if (message.equalsIgnoreCase("cls")) {
						chatBox.setText("");
						chatBox.setCaretPosition(0);
					} else {
						AdminClient.getConnection()
								.addOutgoingPacket(new MessagePacket(null, MessagePacket.GLOBAL_CHAT, message));
					}
				}
			}

		});
		chatPanel.add(chatField, BorderLayout.SOUTH);
		chatField.setColumns(10);
	}

	public ServerModuleComponent getComponentForModuleName(final String moduleName) {
		for (ServerModuleComponent component : serverModuleList) {
			if (component.getModuleName().equalsIgnoreCase(moduleName)) {
				return component;
			}
		}
		return null;
	}

}
