package com.git.cs309.mmoclient.packets;

import javax.swing.JOptionPane;

import com.git.cs309.mmoclient.gui.GameGUI;
import com.git.cs309.mmoclient.gui.LoginGUI;
import com.git.cs309.mmoserver.packets.ErrorPacket;
import com.git.cs309.mmoserver.packets.MessagePacket;
import com.git.cs309.mmoserver.packets.EventPacket;
import com.git.cs309.mmoserver.packets.Packet;

public final class PacketHandler {
	
	public static void handlePacket(final Packet packet) {
		switch (packet.getPacketType()) {
		case MESSAGE_PACKET:
			MessagePacket messagePacket = (MessagePacket) packet;
			throw new RuntimeException("Handle messages here.");
			//break; Uncomment this after removing exception.
		case ERROR_PACKET:
			ErrorPacket errorPacket = (ErrorPacket) packet;
			switch (errorPacket.getErrorCode()) {
			case ErrorPacket.GENERAL_ERROR:
				System.out.println("Recieved error from server: "+errorPacket.getErrorMessage());
				break;
			case ErrorPacket.LOGIN_ERROR:
				JOptionPane.showMessageDialog(null, "Error: "+errorPacket.getErrorMessage());
				break;
			default:
				System.err.println("No case for error code: "+errorPacket.getErrorCode());
				System.err.println("Error message: "+errorPacket.getErrorMessage());
				break;
			}
			break;
		case EVENT_PACKET:
			EventPacket eventPacket = (EventPacket) packet;
			switch (eventPacket.getEventCode()) {
			case EventPacket.LOGIN_SUCCESS:
				LoginGUI.getSingleton().setVisible(false);
				throw new RuntimeException("Set your Game GUI to visible here."); // (Old Code) GameGUI.getSingleton().setVisible(true);
				//break; YOU'RE gunna need to uncomment this once you remove the exception
			default:
				System.err.println("No case for event code: "+eventPacket.getEventCode());
				break;
			}
			break;
		case ENTITY_UPDATE_PACKET:
			//This packet means that something has happened to an entity that already exists.
			throw new RuntimeException("Handle updating entities here");
		case EXTENSIVE_CHARACTER_PACKET:
			//This packet is telling the client that there is a new character that needs to be managed by the client.
			throw new RuntimeException("Handle new characters.");
		case EXTENSIVE_OBJECT_PACKET:
			throw new RuntimeException("Handle new objects here.");
		case PLAYER_CHARACTER_PACKET:
			throw new RuntimeException("Handle new players here.");
		case TEST_PACKET:
			System.out.println("No code for test packet");
			break;
		case SERVER_MODULE_STATUS_PACKET: // No need to do anything with these packets, they're for the admin client or only expected for the client to send to server.
		case INTERFACE_CLICK_PACKET:
		case LOGIN_PACKET:
		case USER_STATUS_PACKET:
		case ADMIN_COMMAND_PACKET:
		case ENTITY_CLICK_PACKET:
			break; 
		default:
			System.out.println("Recieved unexpected packet type: "+packet.getPacketType());
			break;
		}
	}
}
