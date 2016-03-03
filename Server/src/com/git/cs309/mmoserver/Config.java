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
	
	public static final int MAX_NAME_LENGTH = 14;

	//This class is just temporary storage for configuration stuff, like maxes and mins
	//Test Push
	//Max bytes per packet.
	public static final int MAX_PACKET_BYTES = 1000;

	public static final int MILLISECONDS_PER_MINUTE = 60000;

	public static final int MILLISECONDS_PER_SECOND = 1000;

	public static final int STATUS_PRINT_RATE = 1; // Minutes between status prints.

	public static final int TICK_RATE = 125; // Ticks per second. Must divide evenly into 1000 for accurate results. (since it uses integer math, the resulting time calculations will be inaccurate)
	
	//Desired tick delay
	public static final long MILLISECONDS_PER_TICK = MILLISECONDS_PER_SECOND / TICK_RATE;
	
	//Speed at which movements in a walking queue are performed.
	public static final long TICKS_PER_WALK = 400 / MILLISECONDS_PER_TICK;
	
	public static final int NPC_WALKING_RATE = TICK_RATE * 60; // 1 in ... 

	public static final String NPC_DEFINITION_PATH = "./data/cfg/NPCDefinitions.xml";

	public static final String OBJECT_DEFINITION_PATH = "./data/cfg/ObjectDefinitions.xml";
	
	public static final String ITEM_DEFINITION_PATH = "./data/cfg/ItemDefinitions.xml";
	
	public static final String NPC_DROPS_PATH = "./data/cfg/NPCDrops.xml";

	public static final String MAP_DEFINITIONS_FOLDER = "./data/map/";

	//Max packets/tick before automatically closes connection.
	public static final int PACKETS_PER_TICK_BEFORE_KICK = 5;

	public static final String PERMISSIONS_PATH = "./data/cfg/permissions.cfg";

	public static final String MODERATIONS_PATH = "./data/cfg/moderations.ser";

	public static final String ENTER_GAME_MESSAGE = "Welcome to Island Depths.";

	public static final int PLAYER_START_X = 0;

	public static final int PLAYER_START_Y = 0;

	public static final int PLAYER_START_Z = 0;
	
	public static final int MAX_WALKING_DISTANCE = 40;

	public static final int REGEN_AMOUNT = 1;

	public static final int TICKS_PER_MINUTE = (int) (MILLISECONDS_PER_MINUTE / MILLISECONDS_PER_TICK);

	public static final int TICKS_PER_REGEN = (int) ((2 * MILLISECONDS_PER_MINUTE) / MILLISECONDS_PER_TICK);
	
	public static final int TICKS_BEFORE_PLAYER_RESPAWN = (TICKS_PER_MINUTE / 60) * 5;
	
	public static final int TICKS_TILL_ITEM_DESPAWN = TICKS_PER_MINUTE * 5;

	public static final String USER_FILE_PATH = "./data/users/";
}
