package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.Connection;
import com.git.cs309.mmoserver.entity.characters.user.User;

public final class MoveHandler {
	public static final void handlePacket(MovePacket packet) {
		User user = ((Connection) packet.getConnection()).getUser();
		if (user == null || !user.isInGame()) {
			return;
		}
		user.getCurrentCharacter().walkTo(packet.getX(), packet.getY());
	}
}
