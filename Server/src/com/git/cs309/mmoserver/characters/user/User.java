package com.git.cs309.mmoserver.characters.user;

import java.io.Serializable;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.ClosedIDSystem;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;

public final class User implements Serializable {

	/**
	 * 
	 */
	//need split between user and users charicter
	private static final long serialVersionUID = 9016268542066197274L;

	private String username;
	private String password;
	private transient AbstractConnection connection; // Transient means serialization will ignore this variable.
	private transient Rights userRights = Rights.PLAYER;
	private transient IDTag idTag;
	private transient int currentCharacter = -1; // -1 = none selected. Other values are indexes of array
	private PlayerCharacter[] playerCharacters = new PlayerCharacter[5]; // Maximum characters per user
	private transient boolean inGame = false;

	public User() {
		currentCharacter = -1;
		inGame = false;
		idTag = ClosedIDSystem.getTag();
	}

	public User(final String username, final String password) {
		this.username = username;
		this.password = password;
		for (int i = 0; i < playerCharacters.length; i++) {
			playerCharacters[i] = new PlayerCharacter(Config.PLAYER_START_X, Config.PLAYER_START_Y);
		}
		idTag = ClosedIDSystem.getTag();
	}

	public void enterGame(int characterIndex) {
		if (!playerCharacters[characterIndex].isCreated()) {

		}
	}

	public PlayerCharacter getCurrentCharacter() {
		if (currentCharacter == -1) {
			return null;
		}
		return playerCharacters[currentCharacter];
	}

	public void setIDTag(IDTag idTag) {
		this.idTag = idTag;
	}

	public int getUniqueID() {
		return idTag.getID();
	}

	public void cleanUp() {

	}

	public void setRights(Rights rights) {
		userRights = rights;
	}

	public Rights getRights() {
		return userRights;
	}

	public AbstractConnection getConnection() {
		return connection;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setConnection(final AbstractConnection connection) {
		this.connection = connection;
	}

	@Override
	public String toString() {
		return username;
	}
}
