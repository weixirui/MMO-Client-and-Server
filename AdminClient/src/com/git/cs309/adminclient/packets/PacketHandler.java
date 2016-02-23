package com.git.cs309.adminclient.packets;

import com.git.cs309.adminclient.gui.ClientGUI;
import com.git.cs309.adminclient.gui.LoginGUI;
import com.git.cs309.adminclient.gui.ServerModuleComponent;
import com.git.cs309.mmoserver.packets.EventPacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.packets.ServerModuleStatusPacket;

public final class PacketHandler {

	public static void handlePacket(final Packet packet) {
		switch (packet.getPacketType()) {
		case MESSAGE_PACKET:
			break;
		case EVENT_PACKET:
			EventPacket eventPacket = (EventPacket) packet;
			switch (eventPacket.getEventCode()) {
			case EventPacket.LOGIN_SUCCESS:
				LoginGUI.getSingleton().setVisible(false);
				ClientGUI.getSingleton().setVisible(true);
				break;
			default:
				System.out.println("No case for event code: "+eventPacket.getEventCode());
			}
			break;
		case SERVER_MODULE_STATUS_PACKET:
			ServerModuleStatusPacket smsp = (ServerModuleStatusPacket) packet;
			ServerModuleComponent component = ClientGUI.getSingleton().getComponentForModuleName(smsp.getName());
			if (component != null) {
				component.setDrag(smsp.getDrag());
			}
			ClientGUI.getSingleton().repaint();
			break;
		}
	}
}
