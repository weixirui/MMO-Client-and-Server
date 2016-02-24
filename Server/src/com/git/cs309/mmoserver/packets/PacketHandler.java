package com.git.cs309.mmoserver.packets;

/**
 * 
 * @author Group 21
 * 
 *         Packet handler. Handles packets. Can also interpret this as a packet
 *         distributer, if the need arises for packets to be handled elsewhere.
 */
public final class PacketHandler {
	public static void handlePacket(final Packet packet) {
		switch (packet.getPacketType()) { // Case for each type of packet.
		case MESSAGE_PACKET:
			MessageHandler.handleMessagePacket((MessagePacket) packet);
			break;
		case LOGIN_PACKET:
			LoginHandler.handleLoginPacket((LoginPacket) packet);
			break;
		case ERROR_PACKET:
			//Not gunna do anything for Error packet since the server shouldn't be getting them in the first place.
			break;
		case TEST_PACKET:
			TestPacket testPacket = (TestPacket) packet;
			switch (testPacket.getTest()) {
			case 0:
				throw new RuntimeException("Just a test.");
			}
			break;
		case ADMIN_COMMAND_PACKET:
			CommandHandler.handleCommandPacket(packet);
			break;
		default:
			System.err.println("No case for type: " + packet.getPacketType() + " in PacketHandler.handlePacket");
		}
	}
}
