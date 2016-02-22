package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public final class PacketFactory {

	public static Packet buildPacket(final byte[] bytes, final AbstractConnection source) {
		switch (bytes[0]) { // First byte should ALWAYS be the type byte.
		case PacketType.NULL_PACKET_BYTE: // Null packet
			return null;
		case PacketType.MESSAGE_PACKET_BYTE: // Message packet
			return new MessagePacket(bytes, source);
		case PacketType.LOGIN_PACKET_BYTE: // Login packet
			return new LoginPacket(bytes, source);
		case PacketType.ERROR_PACKET_BYTE: // Error packet
			return new ErrorPacket(bytes, source);
		case PacketType.EVENT_PACKET_BYTE:
			return new EventPacket(bytes, source);
		case PacketType.ENTITY_UPDATE_PACKET_BYTE:
			return new EntityUpdatePacket(bytes, source);
		case PacketType.TEST_PACKET_BYTE:
			return new TestPacket(source, bytes);
		default:
			System.out.println("No case for type byte: " + bytes[0]);
			return null;
		}
	}

	private PacketFactory() {
		// To prevent instantiation.
	}
}
