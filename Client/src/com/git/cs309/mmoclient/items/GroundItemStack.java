package com.git.cs309.mmoclient.items;

import java.util.ArrayList;
import java.util.List;

public final class GroundItemStack {
	private final List<ItemStack> groundItems;
	private final int x, y;

	public GroundItemStack(int x, int y) {
		groundItems = new ArrayList<ItemStack>();
		this.x = x;
		this.y = y;
	}

	public void addItemStack(ItemStack stack) {
		groundItems.add(stack);
	}

	public ItemStack getStack(int index) {
		assert index < groundItems.size() && index >= 0;
		return groundItems.get(index);
	}

	public ItemStack removeStack(int index) {
		assert index < groundItems.size() && index >= 0;
		return groundItems.remove(index);
	}

	public List<ItemStack> getStack() {
		return groundItems;
	}

	public int getStackSize() {
		return groundItems.size();
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
}
