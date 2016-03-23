package com.git.cs309.mmoserver.items;

import com.git.cs309.mmoserver.packets.ItemContainerPacket;

public enum EquipmentSlot {
	HELMET(ItemContainerPacket.HELMET_SLOT), CHEST(ItemContainerPacket.CHEST_SLOT), LEFT_HAND(ItemContainerPacket.LEFT_HAND),
	RIGHT_HAND(ItemContainerPacket.RIGHT_HAND), LEGS(ItemContainerPacket.LEGS_SLOT), BOOTS(ItemContainerPacket.BOOTS_SLOT),
	GLOVES(ItemContainerPacket.GLOVES_SLOT), NO_SLOT(-1), CAPE(ItemContainerPacket.CAPE_SLOT),
	AMMO(ItemContainerPacket.AMMO_SLOT), BOTH_HANDS(-1);
	
	private final int slotIndex;
	
	private EquipmentSlot(final int slotIndex) {
		this.slotIndex = slotIndex;
	}
	
	public int getSlotIndex() {
		return slotIndex;
	}
}
