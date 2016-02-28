package com.git.cs309.mmoserver.items;

import com.git.cs309.mmoserver.combat.CombatStyle;

public final class ItemStack {
	private final ItemDefinition definition;
	private volatile int count;
	
	public ItemStack(final ItemDefinition definition) {
		assert definition != null;
		this.definition = definition;
		this.count = 1;
	}
	
	public ItemStack(final ItemDefinition definition, int count) {
		assert definition != null && ((count > 0 && definition.isStackable()) || count == 1);
		this.definition = definition;
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	public void addToCount(int toAdd) {
		assert isStackable();
		count += toAdd;
	}
	
	public void removeFromCount(int toRemove) {
		assert count >= toRemove;
		count -= toRemove;
	}
	
	public boolean isStackable() {
		return definition.isStackable();
	}
	
	public int getId() {
		return definition.getId();
	}
	
	public int getPrice() {
		return definition.getPrice() * count;
	}
	
	public int getStrength() {
		return definition.getStrength();
	}
	
	public int getDefence() {
		return definition.getDefence();
	}
	
	public String getItemName() {
		return definition.getItemName();
	}
	
	public CombatStyle getStyle() {
		return definition.getStyle();
	}
	
	public EquipmentSlot getSlot() {
		return definition.getSlot();
	}
	
	public int getLevel() {
		return definition.getLevel();
	}
}
