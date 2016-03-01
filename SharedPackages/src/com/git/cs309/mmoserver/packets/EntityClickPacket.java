package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.BinaryOperations;

public class EntityClickPacket extends Packet {
	private final int entityID;
	private final int uniqueID;
	private final int entityX;
	private final int entityY;
	private final int clickArgs;

	public EntityClickPacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		int[] values = BinaryOperations.intArrayFromBytes(bytes, 1);
		entityID = values[0];
		uniqueID = values[1];
		entityX = values[2];
		entityY = values[3];
		clickArgs = values[4];
	}

	public EntityClickPacket(AbstractConnection source, final int entityID, final int uniqueID, final int entityX,
			final int entityY, final int clickArgs) {
		super(source);
		this.entityID = entityID;
		this.uniqueID = uniqueID;
		this.entityX = entityX;
		this.entityY = entityY;
		this.clickArgs = clickArgs;
	}

	public int getClickArgs() {
		return clickArgs;
	}

	public int getEntityID() {
		return entityID;
	}

	public int getEntityX() {
		return entityX;
	}

	public int getEntityY() {
		return entityY;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_CLICK_PACKET;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	@Override
	public int sizeOf() {
		return 21;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[sizeOf()];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		for (byte b : BinaryOperations.toBytes(entityID, uniqueID, entityX, entityY, clickArgs)) {
			bytes[index++] = b;
		}
		return bytes;
	}

}
