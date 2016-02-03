package com.git.cs309.mmoclient.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.util.Queue;

import com.git.cs309.mmoserver.util.CycleQueue;

public final class ChatComponent extends ScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7928431372008332240L;
	public static volatile MessageGroup currentGroup = MessageGroup.ALL;
	private static final Queue<ChatMessage> allMessages = new CycleQueue<>(1000, true);
	private static final Queue<ChatMessage> partyMessages = new CycleQueue<>(1000, true);
	private static final Queue<ChatMessage> globalMessages = new CycleQueue<>(1000, true);
	private static final Queue<ChatMessage> localMessages = new CycleQueue<>(1000, true);
	private static final Queue<ChatMessage> privateMessages = new CycleQueue<>(1000, true);
	private static final Color backgroundColor = new Color(0x7F, 0x7F, 0x7F, 0x7F);
	private static final Scrollbar scrollBar = new Scrollbar();
	
	private static final ChatComponent SINGLETON = new ChatComponent();
	
	private ChatComponent() {
		setSize(400, 300);
		setLayout(new BorderLayout());
	}
	
	public static ChatComponent getSingleton() {
		return SINGLETON;
	}

	public static void addMessage(final MessageGroup group, final String message) {
		ChatMessage chatMessage = new ChatMessage(group, message);
		switch (group) {
		case PARTY:
			partyMessages.add(chatMessage);
			break;
		case GLOBAL:
			globalMessages.add(chatMessage);
			break;
		case LOCAL:
			localMessages.add(chatMessage);
			break;
		case PRIVATE:
			privateMessages.add(chatMessage);
			break;
		default:
			System.err.println("No case for message group: " + group);
		}
		allMessages.add(chatMessage);
		updateScrollBar();
	}
	
	private static void updateScrollBar() {
		scrollBar.setMinimum(0);
		switch (currentGroup) {
		case ALL:
			scrollBar.setMaximum(allMessages.size());
			break;
		case PARTY:
			scrollBar.setMaximum(partyMessages.size());
			break;
		case GLOBAL:
			scrollBar.setMaximum(globalMessages.size());
			break;
		case LOCAL:
			scrollBar.setMaximum(localMessages.size());
			break;
		case PRIVATE:
			scrollBar.setMaximum(privateMessages.size());
			break;
		default:
			System.err.println("No case for type: "+currentGroup);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		synchronized (getTreeLock()) {
			for (Component component : this.getComponents()) {
				component.paint(g);
			}
		}
	}
}
