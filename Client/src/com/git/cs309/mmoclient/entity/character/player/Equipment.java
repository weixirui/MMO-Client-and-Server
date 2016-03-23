package com.git.cs309.mmoclient.entity.character.player;

import com.git.cs309.mmoclient.items.ItemContainer;
import com.git.cs309.mmoclient.items.ItemStack;
import com.git.cs309.mmoserver.packets.ItemContainerPacket;

public class Equipment extends ItemContainer {
	public static final int HELMET_SLOT = ItemContainerPacket.HELMET_SLOT;
	public static final int CHEST_SLOT = ItemContainerPacket.CHEST_SLOT;
	public static final int LEGS_SLOT = ItemContainerPacket.LEGS_SLOT;
	public static final int BOOTS_SLOT = ItemContainerPacket.BOOTS_SLOT;
	public static final int GLOVES_SLOT = ItemContainerPacket.GLOVES_SLOT;
	public static final int CAPE_SLOT = ItemContainerPacket.CAPE_SLOT;
	public static final int RIGHT_HAND = ItemContainerPacket.RIGHT_HAND;
	public static final int LEFT_HAND = ItemContainerPacket.LEFT_HAND;
	public static final int AMMO_SLOT = ItemContainerPacket.AMMO_SLOT;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3779426645898182703L;

	public Equipment() {
		super(9);
	}

	public ItemStack getEquipment(int slot) {
		return getItemStack(slot);
	}

	private void putInSlot(ItemStack item, int slot) {
		items[slot] = item;
		if (item == null) {
			count--;
		}
	}

	public void equip(ItemStack item) {
		this.putInSlot(item, item.getSlot().getSlotIndex());
	}
}
