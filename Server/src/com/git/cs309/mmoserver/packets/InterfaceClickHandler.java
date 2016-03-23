package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.Connection;
import com.git.cs309.mmoserver.entity.characters.user.User;
@SuppressWarnings("unused")
public final class InterfaceClickHandler {
	public static final void handlePacket(final InterfaceClickPacket packet) {
		User user = ((Connection) packet.getConnection()).getUser();
		switch (packet.getInterfaceID()) {
		case InterfaceClickPacket.ITEM_CLICK:
			int containerId = packet.getClickArgs1();
			int itemIndex = packet.getClickArgs2();
			//TODO Implement properly
			break;
		case InterfaceClickPacket.MENU_CLICK:
			int menuId = packet.getClickArgs1();
			int menuIndex = packet.getClickArgs2();
			//TODO Implement properly
			break;
		case InterfaceClickPacket.CANCEL_BUTTON:
		case InterfaceClickPacket.CHARACTER_1_SLOT:
			user.enterGame(0);
			break;
		case InterfaceClickPacket.CHARACTER_2_SLOT:
			user.enterGame(1);
			break;
		case InterfaceClickPacket.CHARACTER_3_SLOT:
			user.enterGame(2);
			break;
		case InterfaceClickPacket.CHARACTER_4_SLOT:
			user.enterGame(3);
			break;
		case InterfaceClickPacket.CHARACTER_5_SLOT:
			user.enterGame(4);
			break;
		case InterfaceClickPacket.ELITE_SKILL:
		case InterfaceClickPacket.HEALING_SKILL:
		case InterfaceClickPacket.OKAY_BUTTON:
		case InterfaceClickPacket.SKILL_ACTION_1:
		case InterfaceClickPacket.SKILL_ACTION_2:
		case InterfaceClickPacket.SKILL_ACTION_3:
		case InterfaceClickPacket.SKILL_ACTION_4:
		case InterfaceClickPacket.SKILL_ACTION_5:
		case InterfaceClickPacket.UTILITY_SKILL_1:
		case InterfaceClickPacket.UTILITY_SKILL_2:
		case InterfaceClickPacket.UTILITY_SKILL_3:
		case InterfaceClickPacket.WEAPON_SWAP:
		default:
			System.out.println("No case for interface ID: "+packet.getInterfaceID());
		}
	}
}
