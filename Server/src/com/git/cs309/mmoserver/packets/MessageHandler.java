package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.connection.Connection;
import com.git.cs309.mmoserver.entity.characters.user.User;
import com.git.cs309.mmoserver.entity.characters.user.UserManager;

public final class MessageHandler {
	public static final void handleMessagePacket(MessagePacket messagePacket) {
		Connection userConnection = (Connection) messagePacket.getConnection();
		if (!userConnection.isLoggedIn()) {
			return; // No need to handle message packets for users not logged in yet.
		}
		String username = userConnection.getUser().isInGame() ? userConnection.getUser().getCurrentCharacter().getName()
				: userConnection.getUser().getUsername();
		String lowercaseMessage = messagePacket.getMessage().toLowerCase();
		if (lowercaseMessage.startsWith("/p ") || lowercaseMessage.startsWith("/party ")) { // Party messages
			//TODO Send message to party members only
		} else if (lowercaseMessage.startsWith("/y ") || lowercaseMessage.startsWith("/yell ")) { // Global (yell) chat
			Main.getConnectionManager().sendPacketToAllConnections(new MessagePacket(null, MessagePacket.GLOBAL_CHAT,
					username + ": " + messagePacket.getMessage().replace("/yell ", "").replace("/y ", "")));
		} else if (lowercaseMessage.startsWith("/w ") || lowercaseMessage.startsWith("/whisper ")) { // Whisper chat
			String[] split = messagePacket.getMessage().split(" ");
			if (split.length < 2) {
				return;
			}
			String otherUsername = split[1]; // Should be the word after /w(hisper)
			if (otherUsername.equalsIgnoreCase(userConnection.getUser().getUsername())) {
				userConnection.addOutgoingPacket(
						new MessagePacket(null, MessagePacket.ERROR_CHAT, "You cannot whisper yourself!"));
				return;
			}
			User other = UserManager.getUserForUsername(otherUsername);
			if (other == null) {
				userConnection.addOutgoingPacket(new MessagePacket(null, MessagePacket.ERROR_CHAT,
						"No user with the username \"" + otherUsername + "\" is currently online."));
				return;
			}
			other.getConnection()
					.addOutgoingPacket(new MessagePacket(null, MessagePacket.PRIVATE_CHAT,
							username + ": " + messagePacket.getMessage().replace("/w " + otherUsername + " ", "")
									.replace("/whisper " + otherUsername + " ", "")));
		} else { // Local chat
			//TODO Send message to local characters only
		}
	}
}
