package com.git.cs309.mmoclient.packets;

import javax.swing.JOptionPane;

import com.git.cs309.mmoclient.Client;
import com.git.cs309.mmoclient.entity.character.npc.NPCFactory;
import com.git.cs309.mmoclient.entity.character.player.PlayerCharacter;
import com.git.cs309.mmoclient.entity.character.player.Self;
import com.git.cs309.mmoclient.entity.object.GameObjectFactory;
import com.git.cs309.mmoclient.gui.characterselection.CharacterSelectionGUI;
import com.git.cs309.mmoclient.gui.game.GameGUI;
import com.git.cs309.mmoclient.gui.interfaces.ChatBox;
import com.git.cs309.mmoclient.gui.login.LoginGUI;
import com.git.cs309.mmoclient.map.MapFactory;
import com.git.cs309.mmoserver.packets.AbstractPacketHandler;
import com.git.cs309.mmoserver.packets.CharacterSelectionDataPacket;
import com.git.cs309.mmoserver.packets.EntityUpdatePacket;
import com.git.cs309.mmoserver.packets.ErrorPacket;
import com.git.cs309.mmoserver.packets.MessagePacket;
import com.git.cs309.mmoserver.packets.NewCharacterDataPacket;
import com.git.cs309.mmoserver.packets.NewMapPacket;
import com.git.cs309.mmoserver.packets.EventPacket;
import com.git.cs309.mmoserver.packets.ExtensiveCharacterPacket;
import com.git.cs309.mmoserver.packets.ExtensiveObjectPacket;
import com.git.cs309.mmoserver.packets.ExtensivePlayerCharacterPacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.packets.SelfPacket;

public final class PacketHandler extends AbstractPacketHandler {
	
	private static final PacketHandler INSTANCE = new PacketHandler();
	
	public static final PacketHandler getInstance() {
		return INSTANCE;
	}
	
	private PacketHandler() {
		
	}

	@Override
	public void handlePacketBlock(Packet packet) {
		packet = packet.getConnection().getPacket(); // to remove packet, since it doesn't do it itself
		switch (packet.getPacketType()) {
		case MESSAGE_PACKET:
			MessagePacket messagePacket = (MessagePacket) packet;
			ChatBox.getInstance().addMessage(messagePacket.getMessage());
			break;
		case ERROR_PACKET:
			ErrorPacket errorPacket = (ErrorPacket) packet;
			switch (errorPacket.getErrorCode()) {
			case ErrorPacket.GENERAL_ERROR:
				System.out.println("Recieved error from server: "+errorPacket.getErrorMessage());
				break;
			case ErrorPacket.LOGIN_ERROR:
			case ErrorPacket.NAME_TOO_LONG_ERROR:
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
			case EventPacket.CREATE_CHARACTER:
				packet.getConnection().addOutgoingPacket(new NewCharacterDataPacket(null, 0, 0, 0, 0, NewCharacterDataPacket.MALE, JOptionPane.showInputDialog("Enter a name for this character:")));
				break;
			default:
				System.err.println("No case for event code: "+eventPacket.getEventCode());
				break;
			}
			break;
		case EXTENSIVE_PLAYER_CHARACTER_PACKET:
			ExtensivePlayerCharacterPacket extensivePlayerCharacterPacket = (ExtensivePlayerCharacterPacket) packet;
			if (extensivePlayerCharacterPacket.getUniqueID() == Client.getSelfId()) {
				Self self = new Self(extensivePlayerCharacterPacket);
				Client.setSelf(self);
				Client.getMap()
				.putEntity(self);
			} else {
				Client.getMap().putEntity(new PlayerCharacter(extensivePlayerCharacterPacket));
			}
			break;
		case TEST_PACKET:
			System.out.println("No code for test packet");
			break;
		case CHARACTER_STATUS_PACKET:
			break;
		case ENTITY_UPDATE_PACKET:
			EntityUpdatePacket entityUpdate = (EntityUpdatePacket) packet;
			if (entityUpdate.getUniqueID() == Client.getSelfId()) {
				switch (entityUpdate.getArgs()) {
				case EntityUpdatePacket.MOVED:
					Client.getSelf().setPosition(entityUpdate.getX(), entityUpdate.getY());
					break;
				}
			} else {
				switch (entityUpdate.getArgs()) {
				case EntityUpdatePacket.MOVED:
					Client.getMap().getEntity(entityUpdate.getUniqueID()).setPosition(entityUpdate.getX(), entityUpdate.getY());
					break;
				case EntityUpdatePacket.REMOVED:
					Client.getMap().removeEntity(entityUpdate.getX(), entityUpdate.getY());
					break;
				}
			}
			break;
		case EXTENSIVE_CHARACTER_PACKET:
			Client.getMap().putEntity(NPCFactory.getInstance().createNPC((ExtensiveCharacterPacket) packet));
			break;
		case EXTENSIVE_OBJECT_PACKET:
			Client.getMap().putEntity(GameObjectFactory.getInstance().createGameObject((ExtensiveObjectPacket) packet));
			break;
		case ITEM_CONTAINER_PACKET:
			break;
		case NEW_MAP_PACKET:
			NewMapPacket newMapPacket = (NewMapPacket) packet;
			Client.setMap(MapFactory.getInstance().createMap(newMapPacket.getMapName()));
			break;
		case PLAYER_EQUIPMENT_PACKET:
			break;
		case SELF_PACKET:
			SelfPacket selfPacket = (SelfPacket) packet;
			Client.setSelfId(selfPacket.getUniqueID());
			CharacterSelectionGUI.getSingleton().setVisible(false);
			GameGUI.getSingleton().setVisible(true);
			break;
		case CHARACTER_SELECTION_DATA_PACKET:
			CharacterSelectionGUI.getSingleton().updateComponents((CharacterSelectionDataPacket) packet); 
			CharacterSelectionGUI.getSingleton().repaint();
			break;
		//NOT HANDLED BY CLIENT
		case LOGIN_PACKET:
		case MOVE_PACKET:
		case NEW_CHARACTER_DATA_PACKET:
		case SERVER_MODULE_STATUS_PACKET:
		case SIMPLE_REQUEST_PACKET:
		case NULL_PACKET:
		case INTERFACE_CLICK_PACKET:
		case ENTITY_CLICK_PACKET:
		case ADMIN_COMMAND_PACKET:
		case USER_STATUS_PACKET:
			break;
		default:
			break;
		}
	}
}
