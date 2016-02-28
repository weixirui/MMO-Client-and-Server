package com.git.cs309.mmoserver.items;

import java.io.Serializable;

public final class ItemContainer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1101038885487284304L;
	private int size;
	private int count = 0;
	private  ItemStack[] items;
	
	public ItemContainer() {
		
	}
	
	public ItemContainer(final int size) {
		this.size = size;
		items = new ItemStack[size];
		for (int i = 0; i < size; i++) {
			items[i] = null;
		}
	}
	
	public ItemStack getItemStack(int index) {
		assert index >= 0 && index < size;
		return items[index];
	}
	
	public void deleteAll() {
		for (int i = 0; i < size; i++) {
			items[i] = null;
		}
	}
	
	public void addItemStack(ItemStack newStack) {
		assert !full() && newStack != null;
		if (newStack.isStackable() && getIndexOfId(newStack.getId()) != -1) {
			getItemStack(getIndexOfId(newStack.getId())).addToCount(newStack.getCount());
			return;
		}
		items[getFirstEmptyIndex()] = newStack;
	}
	
	public void removeItem(int itemId, int count) {
		assert hasItemId(itemId) && items[getIndexOfId(itemId)].getCount() >= count;
		int index = getIndexOfId(itemId);
		items[index].removeFromCount(count);
		if (items[index].getCount() == 0) {
			items[index] = null;
		}
	}
	
	public boolean full() {
		return count == size;
	}
	
	public boolean hasItemId(int itemId) {
		for (int i = 0; i < size; i++) {
			if (items[i] != null && items[i].getId() == itemId) {
				return true;
			}
		}
		return false;
	}
	
	public int getFirstEmptyIndex() {
		for (int i = 0; i < size; i++) {
			if (items[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public int getIndexOfId(int itemId) {
		for (int i = 0; i < size; i++) {
			if (items[i] != null && items[i].getId() == itemId) {
				return i;
			}
		}
		return -1;
	}
}
