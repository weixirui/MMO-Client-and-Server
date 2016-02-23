package com.git.cs309.mmoserver.characters.user;

import java.io.Serializable;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.packets.EventPacket;
import com.git.cs309.mmoserver.util.ClosedIDSystem;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;
import com.git.cs309.mmoserver.util.WordUtils;

public final class User implements Serializable {

	/**
	 * 
	 */
	//need split between user and users charicter
	private static final long serialVersionUID = 9016268542066197274L;

	private transient AbstractConnection connection; // Transient means serialization will ignore this variable.
	private transient int currentCharacter = -1; // -1 = none selected. Other values are indexes of array
	private transient IDTag idTag;
	private transient boolean inGame = false;
	private String password;
	private PlayerCharacter[] playerCharacters = new PlayerCharacter[5]; // Maximum characters per user
	private String username;
	private transient Rights userRights = Rights.PLAYER;

	public User() {
		currentCharacter = -1;
		inGame = false;
		idTag = ClosedIDSystem.getTag();
	}

	public User(final String username, final String password) {
		this.username = WordUtils.capitalizeText(username);
		this.password = password;
		for (int i = 0; i < playerCharacters.length; i++) {
			playerCharacters[i] = new PlayerCharacter(Config.PLAYER_START_X, Config.PLAYER_START_Y);
		}
		idTag = ClosedIDSystem.getTag();
	}

	public void cleanUp() {
		exitCurrentCharacter();
	}

	public void enterGame(int characterIndex) {
		if (!playerCharacters[characterIndex].isCreated()) {
			connection.addOutgoingPacket(new EventPacket(null, EventPacket.CREATE_CHARACTER));
			return;
		}
		if (inGame) {
			System.err.println("User already in the game.");
			return;
		}
		playerCharacters[characterIndex].setIDTag(this.idTag);
		playerCharacters[characterIndex].addToManager();
		currentCharacter = characterIndex;
		inGame = true;
	}

	public void exitCurrentCharacter() {
		inGame = false;
		currentCharacter = -1;
		PlayerCharacter character = getCurrentCharacter();
		if (character != null) {
			Main.getCharacterManager().removeCharacter(character);
			character.cleanUp();
		}
	}

	public AbstractConnection getConnection() {
		return connection;
	}

	public PlayerCharacter getCurrentCharacter() {
		if (currentCharacter == -1) {
			return null;
		}
		return playerCharacters[currentCharacter];
	}

	public String getPassword() {
		return password;
	}

	public Rights getRights() {
		return userRights;
	}

	public int getUniqueID() {
		return idTag.getID();
	}

	public String getUsername() {
		return username;
	}

	public void setConnection(final AbstractConnection connection) {
		this.connection = connection;
	}

	public void setIDTag(IDTag idTag) {
		this.idTag = idTag;
	}

	public void setRights(Rights rights) {
		userRights = rights;
	}

	@Override
	public String toString() {
		return username;
	}
}
