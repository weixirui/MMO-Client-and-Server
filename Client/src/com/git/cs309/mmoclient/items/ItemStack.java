package com.git.cs309.mmoclient.items;

public final class ItemStack {
	private final ItemDefinition definition;
	private volatile int count;
	
	public ItemStack(final ItemDefinition definition) {
		assert definition != null;
		this.definition = definition;
		this.count = 1;
	}
	
	public ItemStack(final ItemDefinition definition, int count) {
		this.definition = definition;
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	public void addToCount(int toAdd) {
		count += toAdd;
	}
	
	public void removeFromCount(int toRemove) {
		assert count >= toRemove;
		count -= toRemove;
	}
	
	public int getId() {
		return definition.getId();
	}
	
	public int getPrice() {
		return definition.getPrice() * count;
	}
	
	public String getItemName() {
		return definition.getItemName();
	}
	
	public EquipmentSlot getSlot() {
		return definition.getSlot();
	}
}
