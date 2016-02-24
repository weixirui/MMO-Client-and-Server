package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.entity.characters.user.IllegalNamingException;
import com.git.cs309.mmoserver.entity.characters.user.InvalidPasswordException;
import com.git.cs309.mmoserver.entity.characters.user.UserAlreadyLoggedInException;
import com.git.cs309.mmoserver.entity.characters.user.UserManager;

public final class LoginHandler {
	public static final void handleLoginPacket(LoginPacket loginPacket) {
		try {
			if (!UserManager.logIn(loginPacket)) {
				System.err.println("Failed to log in user \"" + loginPacket.getUsername() + "\".");
				loginPacket.getConnection().addOutgoingPacket(
						new ErrorPacket(loginPacket.getConnection(), ErrorPacket.LOGIN_ERROR, "Login failed."));
			} else {
				loginPacket.getConnection()
						.addOutgoingPacket(new EventPacket(loginPacket.getConnection(), EventPacket.LOGIN_SUCCESS));
			}
		} catch (UserAlreadyLoggedInException e) {
			loginPacket.getConnection()
					.addOutgoingPacket(new ErrorPacket(loginPacket.getConnection(), ErrorPacket.LOGIN_ERROR,
							"User with username \"" + loginPacket.getUsername() + "\" is already logged in."));
		} catch (InvalidPasswordException e) {
			loginPacket.getConnection()
					.addOutgoingPacket(new ErrorPacket(loginPacket.getConnection(), ErrorPacket.LOGIN_ERROR,
							"Password for user \"" + loginPacket.getUsername() + "\" does not match."));
		} catch (IllegalNamingException e) {
			loginPacket.getConnection().addOutgoingPacket(
					new ErrorPacket(loginPacket.getConnection(), ErrorPacket.GENERAL_ERROR, e.getMessage()));
		}
	}
}
