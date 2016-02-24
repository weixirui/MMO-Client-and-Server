package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.characters.user.Rights;
import com.git.cs309.mmoserver.connection.Connection;

public final class CommandHandler {
	public static final void handleCommandPacket(final Packet packet) {
		switch (packet.getPacketType()) {
		case ADMIN_COMMAND_PACKET:
			AdminCommandPacket adminPacket = (AdminCommandPacket) packet;
			if (((Connection) adminPacket.getConnection()).isLoggedIn()
					&& ((Connection) adminPacket.getConnection()).getUser().getRights() != Rights.ADMIN) {
				adminPacket.getConnection().addOutgoingPacket(new ErrorPacket(null, ErrorPacket.PERMISSION_ERROR,
						"You do not have the correct permissions to do that."));
				return;
			}
			switch (adminPacket.getCommand()) {
			case AdminCommandPacket.RESTART_SERVER:
				Main.requestExit();
				break;
			case AdminCommandPacket.RESTART_CHARACTER_MANAGER:
				Main.loadAndStartCharacterManager();
				break;
			case AdminCommandPacket.RESTART_CONNECTION_MANAGER:
				Main.loadAndStartConnectionManager();
				break;
			case AdminCommandPacket.RESTART_CYCLE_PROCESS_MANAGER:
				Main.loadAndStartCycleProcessManager();
				break;
			case AdminCommandPacket.RESTART_NPC_MANAGER:
				Main.loadAndStartNPCManager();
				break;
			case AdminCommandPacket.BAN_USER:
				final int userID = adminPacket.getAdditionalField(); // This is the userID of the user to ban.
				final int duration = adminPacket.getDuration(); // In hours
				//TODO Add moderation handler.
				break;
			default:
				System.err.println("No case for admin command " + adminPacket.getCommand()+" in CommandHandler.handleCommandPacket");
			}
			break;
		default:
			System.err.println("No case for packet type \""+packet.getPacketType()+"\" in CommandHandler.handleCommandPacket");
			break;
		}
	}
}
