package com.git.cs309.mmoserver.entity.characters.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.connection.Connection;
import com.git.cs309.mmoserver.packets.ErrorPacket;
import com.git.cs309.mmoserver.packets.LoginPacket;
import com.git.cs309.mmoserver.util.ClosedIDSystem;

/**
 * 
 * @author Group 21
 *
 *         Static utility class that handles user management. This class does
 *         not do anything to the users except load them, and store them into
 *         tables for easy access.
 */
public final class UserManager {
	private static final Hashtable<String, User> IP_TABLE = new Hashtable<>(); // User table with IP as key
	private static final Hashtable<String, Rights> RIGHTS_TABLE = new Hashtable<>();
	private static final Hashtable<String, User> USER_TABLE = new Hashtable<>(); // User table with Username as key

	static {
		try {
			reloadRights();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Creates a new File object representing the file associated with the
	 * username.
	 * 
	 * @param username
	 *            username of file to path to.
	 * @return new File object of user file.
	 */
	public static File getUserFile(final String username) { // Get user file 
		return new File(Config.USER_FILE_PATH + username.toLowerCase() + ".user");
	}

	/**
	 * Grabs a User object from the IP_TABLe based on the IP provided. User must
	 * already be logged in.
	 * 
	 * @param ip
	 *            IP of user to get from IP_TABLE
	 * @return User object with the same IP, or null if there is none.
	 */
	public static User getUserForIP(final String ip) { // Get user for their IP
		return IP_TABLE.get(ip);
	}

	public static User getUserForUserID(final int userID) {
		synchronized (USER_TABLE) {
			for (String key : USER_TABLE.keySet()) {
				if (USER_TABLE.get(key).getUniqueID() == userID) {
					return USER_TABLE.get(key);
				}
			}
		}
		return null;
	}

	/**
	 * Grabs a User object from the USER_TABLE based on the Username provided.
	 * User must already be logged in.
	 * 
	 * @param username
	 *            Username of the user to get from USER_TABLE
	 * @return User object with the same Username, or null if there is none.
	 */
	public static User getUserForUsername(final String username) { // Get user for their Username
		assert isLoggedIn(username);
		return USER_TABLE.get(username.toLowerCase());
	}

	/**
	 * Checks if the user is logged in by checking if the USER_TABLE contains a
	 * user by the same username.
	 * 
	 * @param username
	 *            username of the user to check if logged in
	 * @return true if user exists in USER_TABLE, false if they don't
	 */
	public static boolean isLoggedIn(final String username) { // Check if user is in tables
		return USER_TABLE.containsKey(username.toLowerCase());
	}

	/**
	 * Perform login for LoginPacket
	 * 
	 * @param loginPacket
	 *            packet containing info to log in with.
	 * @return true if successful, false if not.
	 * @throws UserAlreadyLoggedInException
	 *             if user already exists in tables.
	 * @throws InvalidPasswordException
	 *             if the LoginPacket contains the wrong password for that user.
	 * @throws IllegalNamingException
	 */
	public static boolean logIn(final LoginPacket loginPacket)
			throws UserAlreadyLoggedInException, InvalidPasswordException, IllegalNamingException { // Perform login
		if (isLoggedIn(loginPacket.getUsername())) {
			throw new UserAlreadyLoggedInException(
					"The user \"" + loginPacket.getUsername() + "\" is already logged in.");
		}
		if (loginPacket.getUsername().length() > Config.MAX_NAME_LENGTH) {
			((Connection)loginPacket.getConnection()).addOutgoingPacket(new ErrorPacket(null, ErrorPacket.NAME_TOO_LONG_ERROR, "That name is too long!"));
		}
		File userFile = getUserFile(loginPacket.getUsername());
		User user;
		if (userFile.exists()) {
			try {
				user = loadUser(userFile);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			//If password for user doesn't match password for loginPacket
			if (!user.getPassword().equals(loginPacket.getPassword())) {
				throw new InvalidPasswordException("The password does not match the password registered to the user \""
						+ user.getUsername() + "\".");
			}
			user.setConnection(loginPacket.getConnection());
			addUserToTables(user);
		} else {
			if (loginPacket.getUsername().contains(" "))
				throw new IllegalNamingException("Usernames for Users cannot contain spaces.");
			user = new User(loginPacket.getUsername(), loginPacket.getPassword());
			user.setConnection(loginPacket.getConnection());
			addUserToTables(user);
		}
		user.setIDTag(ClosedIDSystem.getTag());
		if (RIGHTS_TABLE.containsKey(user.getUsername().toLowerCase())) {
			user.setRights(RIGHTS_TABLE.get(user.getUsername().toLowerCase()));
		} else {
			user.setRights(Rights.PLAYER);
		}
		((Connection) loginPacket.getConnection()).setUser(user);
		((Connection) loginPacket.getConnection()).setLoggedIn(true);
		user.initialize();
		System.out.println(user.getRights() + " " + user + " logged in.");
		return true;
	}

	/**
	 * Logs out the user with the same username as the one provided. Does
	 * nothing if user isn't logged in. The logout process entails saving the
	 * user, removing the user from the tables, and then calling cleanUp on the
	 * User object.
	 * 
	 * @param username
	 *            username of the User to be logged out.
	 * @return always true. (currently)
	 */
	public static boolean logOut(final String username) {
		if (!isLoggedIn(username)) {
			return true;
		}
		User user = getUserForUsername(username);
		assert user != null;
		try {
			saveUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeUserFromTables(user);
		System.out.println(user.getRights() + " " + user + " logged out.");
		user.cleanUp();
		return true;
	}

	/**
	 * Saves all users.
	 */
	public static void saveAllUsers() {
		for (String key : USER_TABLE.keySet()) {
			try {
				saveUser(USER_TABLE.get(key));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setRights(String username, Rights rights) {
		File permissionsFile = new File(Config.PERMISSIONS_PATH);
		File tempFile = new File(Config.PERMISSIONS_PATH + "_temp");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(permissionsFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String line = "";
			boolean writeNextLine = false;
			while (!(line = reader.readLine()).equalsIgnoreCase("[EOF]")) {
				if ((line.equalsIgnoreCase("[MOD]") && rights == Rights.MOD)
						|| (line.equalsIgnoreCase("[ADMIN]") && rights == Rights.ADMIN)) {
					writeNextLine = true;
					writer.write(line);
					writer.newLine();
					continue;
				}
				if ((line.equalsIgnoreCase(username))) {
					continue;
				}
				if (writeNextLine) {
					writer.write(username);
					writer.newLine();
					writer.write(line);
					writer.newLine();
				} else {
					writer.write(line);
					writer.newLine();
				}
			}
			writer.write(line);
			writer.newLine();
			writer.close();
			reader.close();
			tempFile.renameTo(permissionsFile);
			if (isLoggedIn(username)) {
				getUserForUsername(username).setRights(rights);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to set rights for user " + username);
		}
	}

	/**
	 * Adds specified user to the user tables based on the users IP and Username
	 * 
	 * @param user
	 */
	private static void addUserToTables(final User user) { // Add user to tables.
		assert user != null;
		USER_TABLE.put(user.getUsername().toLowerCase(), user);
		IP_TABLE.put(((Connection) user.getConnection()).getServerSideIP(), user);
	}

	/**
	 * Load user from File representing path to user.
	 * 
	 * @param userFile
	 *            File path to user.
	 * @return deserialized user
	 * @throws FileNotFoundException
	 *             if File doesn't exist.
	 * @throws IOException
	 *             if there is some kind of IOException
	 */
	private static User loadUser(final File userFile) throws FileNotFoundException, IOException { // Load user from file
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(userFile));
		User user = null;
		try {
			user = (User) in.readObject(); // Deserialize user.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		in.close();
		return user;
	}

	private static void reloadRights() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(Config.PERMISSIONS_PATH));
		String line = "";
		Rights currentRights = Rights.PLAYER;
		RIGHTS_TABLE.clear();
		while (!(line = reader.readLine()).equalsIgnoreCase("[EOF]")) {
			switch (line.toUpperCase()) {
			case "[ADMIN]":
				currentRights = Rights.ADMIN;
				break;
			case "[MOD]":
				currentRights = Rights.MOD;
				break;
			default:
				RIGHTS_TABLE.put(line.toLowerCase(), currentRights);
			}
		}
		reader.close();
	}

	/**
	 * Removes the user from the tables.
	 * 
	 * @param user
	 *            user to remove from tables.
	 */
	private static void removeUserFromTables(final User user) {
		assert user != null;
		USER_TABLE.remove(user.getUsername().toLowerCase());
		IP_TABLE.remove(((Connection) user.getConnection()).getServerSideIP());
	}

	/**
	 * Save individual user. Creates an output stream to the user file,
	 * serializes user object, and closes stream.
	 * 
	 * @param user
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void saveUser(final User user) throws FileNotFoundException, IOException {
		assert user != null;
		File userSaveDirectory = new File(Config.USER_FILE_PATH);
		if (!userSaveDirectory.exists()) {
			userSaveDirectory.mkdirs();
		}
		File userFile = getUserFile(user.getUsername());
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userFile));
		out.writeObject(user);
		out.close();
	}

	private UserManager() {
		assert false;//To prevent instantiation, since this is a static utility class
	}
}
