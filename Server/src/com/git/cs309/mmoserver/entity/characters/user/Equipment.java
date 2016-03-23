package com.git.cs309.mmoserver.entity.characters.user;

import com.git.cs309.mmoserver.items.EquipmentSlot;
import com.git.cs309.mmoserver.items.ItemContainer;
import com.git.cs309.mmoserver.items.ItemStack;
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

	public void equip(User user, ItemStack item) {
		if (item.getSlot() == EquipmentSlot.NO_SLOT) {
			return;
		}
		ItemContainer inventory = user.getCurrentCharacter().getInventory();
		if (item.getSlot() == EquipmentSlot.BOTH_HANDS
				&& ((getEquipment(LEFT_HAND) != null && getEquipment(LEFT_HAND).getSlot() != EquipmentSlot.BOTH_HANDS)
						|| (getEquipment(RIGHT_HAND) != null
								&& getEquipment(RIGHT_HAND).getSlot() != EquipmentSlot.BOTH_HANDS))) {
			ItemStack left = getEquipment(LEFT_HAND);
			ItemStack right = getEquipment(RIGHT_HAND);
			int total = 0;
			total += left == null ? 0 : 1;
			total += right == null ? 0 : 1;
			if (inventory.slotsLeft() < total) {
				return;
			}
			putInSlot(item, RIGHT_HAND);
			putInSlot(item, LEFT_HAND);
			if (left != null)
				inventory.addItemStack(left);
			if (right != null)
				inventory.addItemStack(right);
			return;
		} else if (item.getSlot() == EquipmentSlot.BOTH_HANDS
				&& getEquipment(LEFT_HAND).getSlot() == EquipmentSlot.BOTH_HANDS) {
			ItemStack old = getEquipment(LEFT_HAND);
			if (old != null && inventory.slotsLeft() < 1)
				return;
			putInSlot(item, RIGHT_HAND);
			putInSlot(item, LEFT_HAND);
			if (old != null)
				inventory.addItemStack(old);
			return;
		} else if (getEquipment(LEFT_HAND).getSlot() == EquipmentSlot.BOTH_HANDS) {
			ItemStack old = getEquipment(LEFT_HAND);
			if (old != null && inventory.slotsLeft() < 1)
				return;
			putInSlot(item, item.getSlot().getSlotIndex());
			if (item.getSlot() == EquipmentSlot.RIGHT_HAND) {
				putInSlot(null, EquipmentSlot.LEFT_HAND.getSlotIndex());
			} else {
				putInSlot(null, EquipmentSlot.RIGHT_HAND.getSlotIndex());
			}
			if (old != null)
				inventory.addItemStack(old);
		}
		ItemStack old = getEquipment(item.getSlot().getSlotIndex());
		if (old != null && inventory.slotsLeft() < 1)
			return;
		putInSlot(item, item.getSlot().getSlotIndex());
		if (old != null)
			inventory.addItemStack(old);
	}
}
