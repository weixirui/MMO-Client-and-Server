package com.git.cs309.mmoclient.packets;

import javax.swing.JOptionPane;

import com.git.cs309.mmoclient.gui.CharacterSelectionGUI;
import com.git.cs309.mmoclient.gui.GameGUI;
import com.git.cs309.mmoclient.gui.LoginGUI;
import com.git.cs309.mmoserver.packets.AbstractPacketHandler;
import com.git.cs309.mmoserver.packets.ErrorPacket;
import com.git.cs309.mmoserver.packets.MessagePacket;
import com.git.cs309.mmoserver.packets.EventPacket;
import com.git.cs309.mmoserver.packets.Packet;

public final class PacketHandler extends AbstractPacketHandler {
	
	private static final PacketHandler INSTANCE = new PacketHandler();
	
	public static final PacketHandler getInstance() {
		return INSTANCE;
	}
	
	private PacketHandler() {
		
	}

	@Override
	public void handlePacketBlock(Packet packet) {
		packet.getConnection().getPacket(); // to remove packet, since it doesn't do it itself
		switch (packet.getPacketType()) {
		case MESSAGE_PACKET:
			MessagePacket messagePacket = (MessagePacket) packet;
			//TODO Implement properly
			System.out.println("Got message: "+messagePacket.getMessage());
			break;
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
				CharacterSelectionGUI.getSingleton().setVisible(true);
				break;
			default:
				System.err.println("No case for event code: "+eventPacket.getEventCode());
				break;
			}
			break;
		case ADMIN_COMMAND_PACKET:
			break;
		case CHARACTER_STATUS_PACKET:
			break;
		case ENTITY_CLICK_PACKET:
			break;
		case ENTITY_UPDATE_PACKET:
			break;
		case EXTENSIVE_CHARACTER_PACKET:
			break;
		case EXTENSIVE_OBJECT_PACKET:
			break;
		case INTERFACE_CLICK_PACKET:
			break;
		case ITEM_CONTAINER_PACKET:
			break;
		case LOGIN_PACKET:
			break;
		case MOVE_PACKET:
			break;
		case NEW_MAP_PACKET:
			break;
		case NULL_PACKET:
			break;
		case PLAYER_CHARACTER_PACKET:
			break;
		case PLAYER_EQUIPMENT_PACKET:
			break;
		case SELF_PACKET:
			CharacterSelectionGUI.getSingleton().setVisible(false);
			GameGUI.getSingleton().setVisible(true);
			break;
		case SERVER_MODULE_STATUS_PACKET:
			break;
		case SIMPLE_REQUEST_PACKET:
			break;
		case TEST_PACKET:
			break;
		case USER_STATUS_PACKET:
			break;
		default:
			break;
		}
	}
}
