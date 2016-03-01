package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.Connection;
import com.git.cs309.mmoserver.entity.characters.user.User;

/**
 * 
 * @author Group 21
 * 
 *         Packet handler. Handles packets. Can also interpret this as a packet
 *         distributer, if the need arises for packets to be handled elsewhere.
 */
public final class PacketHandler extends AbstractPacketHandler {
	private static final PacketHandler INSTANCE = new PacketHandler();

	public static final PacketHandler getInstance() {
		return INSTANCE;
	}

	private PacketHandler() {

	}

	@Override
	public void handlePacketBlock(Packet packet) {
		switch (packet.getPacketType()) { // Case for each type of packet.
		case MESSAGE_PACKET:
			MessageHandler.handlePacket((MessagePacket) packet);
			break;
		case LOGIN_PACKET:
			LoginHandler.handlePacket((LoginPacket) packet);
			break;
//		case TEST_PACKET:
//			TestPacket testPacket = (TestPacket) packet;
//			switch (testPacket.getTest()) {
//			case 0:
//				throw new RuntimeException("Just a test.");s
//			}
//			break;
		case ADMIN_COMMAND_PACKET:
			CommandHandler.handlePacket(packet);
			break;
		case MOVE_PACKET:
			MoveHandler.handlePacket((MovePacket) packet);
			break;
		case ENTITY_CLICK_PACKET:
			EntityClickHandler.handlePacket((EntityClickPacket) packet);
			break;
		case INTERFACE_CLICK_PACKET:
			InterfaceClickHandler.handlePacket((InterfaceClickPacket) packet);
			break;
		case SIMPLE_REQUEST_PACKET:
			RequestHandler.handlePacket((SimpleRequestPacket) packet);
			break;
		//These next few packets shouldn't occur on the server-side, since they're meant for the client.
		case NEW_MAP_PACKET:
		case ERROR_PACKET:
		case NULL_PACKET:
		case EXTENSIVE_PLAYER_CHARACTER_PACKET:
		case PLAYER_EQUIPMENT_PACKET:
		case SELF_PACKET:
		case SERVER_MODULE_STATUS_PACKET:
		case USER_STATUS_PACKET:
		case CHARACTER_STATUS_PACKET:
		case ENTITY_UPDATE_PACKET:
		case EVENT_PACKET:
		case EXTENSIVE_CHARACTER_PACKET:
		case EXTENSIVE_OBJECT_PACKET:
		case ITEM_CONTAINER_PACKET:
			break;
		case TEST_PACKET:
			break;
		case CHARACTER_SELECTION_DATA_PACKET:
			break;
		case NEW_CHARACTER_DATA_PACKET:
			NewCharacterDataPacket data = (NewCharacterDataPacket) packet;
			User user = ((Connection) data.getConnection()).getUser();
			user.createCharacter(data.getName(), data.getGender(), data.getEyeColor(), data.getSkinColor(), data.getHairColor(), data.getHairStyle());
			break;
		default:
			break;
		}
	}
}
