package com.git.cs309.adminclient.packets;

import com.git.cs309.adminclient.gui.ClientGUI;
import com.git.cs309.adminclient.gui.LoginGUI;
import com.git.cs309.adminclient.gui.ServerModuleComponent;
import com.git.cs309.mmoserver.packets.AbstractPacketHandler;
import com.git.cs309.mmoserver.packets.EventPacket;
import com.git.cs309.mmoserver.packets.MessagePacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.packets.ServerModuleStatusPacket;

public final class PacketHandler extends AbstractPacketHandler {
	
	private static final PacketHandler INSTANCE = new PacketHandler();
	
	public static final PacketHandler getInstance() {
		return INSTANCE;
	}
	
	private PacketHandler() {
		
	}
	
	@Override
	public void handlePacketBlock(Packet packet) {
		packet.getConnection().getPacket(); // To remove packet, since it doesn't do it itself
		switch (packet.getPacketType()) {
		case MESSAGE_PACKET:
			MessagePacket message = (MessagePacket) packet;
			String messagePrefix = "";
			switch (message.getMessageCode()) {
			case MessagePacket.GLOBAL_CHAT:
				messagePrefix = "[GLOBAL] ";
				break;
			case MessagePacket.LOCAL_CHAT:
				messagePrefix = "[LOCAL] ";
				break;
			case MessagePacket.PARTY_CHAT:
				messagePrefix = "[PARTY] ";
				break;
			case MessagePacket.PRIVATE_CHAT:
				messagePrefix = "[PRIVATE] ";
				break;
			case MessagePacket.ERROR_CHAT:
				messagePrefix = "[ERROR] ";
				break;
			case MessagePacket.GAME_CHAT:
				messagePrefix = "[GAME] ";
				break;
			}
			ClientGUI.addMessage(messagePrefix + message.getMessage());
			break;
		case EVENT_PACKET:
			EventPacket eventPacket = (EventPacket) packet;
			switch (eventPacket.getEventCode()) {
			case EventPacket.LOGIN_SUCCESS:
				LoginGUI.getSingleton().setVisible(false);
				ClientGUI.getSingleton().setVisible(true);
				break;
			default:
				System.out.println("No case for event code: " + eventPacket.getEventCode());
			}
			break;
		case SERVER_MODULE_STATUS_PACKET:
			ServerModuleStatusPacket smsp = (ServerModuleStatusPacket) packet;
			ServerModuleComponent component = ClientGUI.getSingleton().getComponentForModuleName(smsp.getName());
			if (component != null) {
				component.setDrag(smsp.getDrag());
			}
			ClientGUI.getSingleton().repaint();
			break;
		case ADMIN_COMMAND_PACKET:
			break;
		case CHARACTER_STATUS_PACKET:
			break;
		case ENTITY_CLICK_PACKET:
			break;
		case ENTITY_UPDATE_PACKET:
			break;
		case ERROR_PACKET:
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
