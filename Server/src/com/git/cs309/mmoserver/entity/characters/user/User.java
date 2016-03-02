package com.git.cs309.mmoserver.entity.characters.user;

import java.io.Serializable;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.packets.CharacterSelectionDataPacket;
import com.git.cs309.mmoserver.packets.EventPacket;
import com.git.cs309.mmoserver.packets.MessagePacket;
import com.git.cs309.mmoserver.packets.SelfPacket;
import com.git.cs309.mmoserver.util.ClosedIDSystem;
import com.git.cs309.mmoserver.util.ClosedIDSystem.IDTag;
import com.git.cs309.mmoserver.util.WordUtils;

/**
 * 
 * @author Group 21
 *
 */
public final class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9016268542066197274L;

	private transient AbstractConnection connection; // Transient means serialization will ignore this variable.
	private transient int currentCharacter = -1; // -1 = none selected. Other values are indexes of array
	private transient IDTag idTag;
	private transient boolean inGame = false;
	private String password;
	private PlayerCharacter[] playerCharacters = new PlayerCharacter[5]; // Maximum characters per user
	private String username;
	private transient Rights userRights = Rights.PLAYER;
	private transient int creatingCharacterIndex = -1;

	public User() {
		//For deserialization only
	}
	
	protected IDTag getIdTag() {
		return idTag;
	}

	public User(final String username, final String password) {
		this.username = WordUtils.capitalizeText(username);
		this.password = password;
		for (int i = 0; i < playerCharacters.length; i++) {
			playerCharacters[i] = new PlayerCharacter(Config.PLAYER_START_X, Config.PLAYER_START_Y);
		}
		initialize();
	}

	public void cleanUp() {
		exitCurrentCharacter();
		cleanUpCharacters();
		idTag.returnTag();
	}

	public void cleanUpCharacters() {
		for (PlayerCharacter character : playerCharacters) {
			character.cleanUp();
		}
	}
	
	public void sendSelectionCharacters(AbstractConnection connection) {
		for (int i = 0; i < playerCharacters.length; i++) {
			if (!playerCharacters[i].isCreated())
				continue;
			connection.addOutgoingPacket(new CharacterSelectionDataPacket(null, i, playerCharacters[i].getEyeColor(), playerCharacters[i].getSkinColor(), playerCharacters[i].getHairColor(), playerCharacters[i].getHairStyle(), playerCharacters[i].getGender(), playerCharacters[i].getName()));
		}
	}
	
	public void createCharacter(String name, byte gender, int eyeColor, int skinColor, int hairColor, int hairStyle) {
		assert creatingCharacterIndex != -1;
		playerCharacters[creatingCharacterIndex].createCharacter(name, gender, eyeColor, skinColor, hairColor, hairStyle);
		enterGame(creatingCharacterIndex);
		creatingCharacterIndex = -1;
	}

	public void enterGame(int characterIndex) {
		if (!playerCharacters[characterIndex].isCreated()) {
			connection.addOutgoingPacket(new EventPacket(null, EventPacket.CREATE_CHARACTER));
			creatingCharacterIndex = characterIndex;
			return;
		}
		if (inGame) {
			System.err.println("User already in the game.");
			return;
		}
		connection.addOutgoingPacket(new SelfPacket(null, getUniqueID()));
		connection.addOutgoingPacket(new MessagePacket(null, MessagePacket.GAME_CHAT, Config.ENTER_GAME_MESSAGE));
		playerCharacters[characterIndex].enterGame(this);
		currentCharacter = characterIndex;
		inGame = true;
	}

	public void exitCurrentCharacter() {
		inGame = false;
		PlayerCharacter character = getCurrentCharacter();
		if (character != null) {
			character.exitGame();
		}
		currentCharacter = -1;
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

	public void initialize() {
		currentCharacter = -1;
		inGame = false;
		idTag = ClosedIDSystem.getTag();
	}

	public boolean isInGame() {
		return inGame;
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
