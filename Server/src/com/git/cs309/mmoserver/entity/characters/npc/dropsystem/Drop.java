package com.git.cs309.mmoserver.entity.characters.npc.dropsystem;

import com.git.cs309.mmoserver.items.ItemFactory;
import com.git.cs309.mmoserver.items.ItemStack;

public final class Drop {
	private final String itemName;
	private final Range range;
	
	public Drop(final String itemName, final Range range) {
		this.itemName = itemName;
		this.range = range;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public Range getRange() {
		return range;
	}
	
	public ItemStack createItemStack() {
		return ItemFactory.getInstance().createItemStack(itemName, range.getAmount());
	}
}
