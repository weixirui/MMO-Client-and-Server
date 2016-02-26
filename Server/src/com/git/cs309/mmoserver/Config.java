package com.git.cs309.mmoserver;

/**
 * 
 * @author Group 21
 *
 */
public final class Config {

	public static final int MAX_PARTY_MEMBERS = 5;

	public static final byte EOF_CHARACTER = -1;

	public static final int GLOBAL_INSTANCE = 0;

	public static final String LOG_BASE_PATH = "./data/logs/";

	//Maximum connections allowed.
	public static final int MAX_CONNECTIONS = 1000;

	public static final int MAX_ENTITIES = 4000;

	//This class is just temporary storage for configuration stuff, like maxes and mins
	//Test Push
	//Max bytes per packet.
	public static final int MAX_PACKET_BYTES = 1000;

	public static final int MILLISECONDS_PER_MINUTE = 60000;

	public static final int MILLISECONDS_PER_SECOND = 1000;

	public static final int TICK_RATE = 125; // Ticks per second. Must divide evenly into 1000 for accurate results. (since it uses integer math, the resulting time calculations will be inaccurate)

	//Desired tick delay
	public static final long MILLISECONDS_PER_TICK = MILLISECONDS_PER_SECOND / TICK_RATE;

	public static final String NPC_DEFINITION_PATH = "./data/cfg/npc_definitions.cfg";

	//Max packets/tick before automatically closes connection.
	public static final int PACKETS_PER_TICK_BEFORE_KICK = 5;

	public static final String PERMISSIONS_PATH = "./data/cfg/permissions.cfg";

	public static final String MODERATIONS_PATH = "./data/cfg/moderations.ser";

	public static final String ENTER_GAME_MESSAGE = "Welcome to Island Depths.";

	public static final int PLAYER_START_X = 0;

	public static final int PLAYER_START_Y = 0;

	public static final int PLAYER_START_Z = 0;

	public static final int REGEN_AMOUNT = 1;

	public static final int TICKS_PER_AUTO_SAVE = (int) ((1 * MILLISECONDS_PER_MINUTE) / MILLISECONDS_PER_TICK);

	public static final int TICKS_PER_MINUTE = (int) (MILLISECONDS_PER_MINUTE / MILLISECONDS_PER_TICK);

	public static final int TICKS_PER_REGEN = (int) ((2 * MILLISECONDS_PER_MINUTE) / MILLISECONDS_PER_TICK);

	public static final String USER_FILE_PATH = "./data/users/";
}
