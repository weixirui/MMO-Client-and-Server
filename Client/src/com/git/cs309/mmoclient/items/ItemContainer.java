package com.git.cs309.mmoclient.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemContainer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1101038885487284304L;
	private int size;
	protected int count = 0;
	protected  ItemStack[] items;
	
	public ItemContainer() {
		
	}
	
	public ItemContainer(final int size) {
		this.size = size;
		items = new ItemStack[size];
		clear();
	}
	
	public ItemStack getItemStack(int index) {
		assert index >= 0 && index < size;
		return items[index];
	}
	
	public void clear() {
		for (int i = 0; i < size; i++) {
			items[i] = null;
		}
	}
	
	public int slotsLeft() {
		return size - count;
	}
	
	public List<ItemStack> toList() {
		List<ItemStack> itemList = new ArrayList<>(count); // Using list because I want to have remove(index)
		for (ItemStack item : items) {
			if (item == null) {
				continue;
			}
			itemList.add(item);
		}
		return itemList;
	}
	
	public List<ItemStack> removeAllAsList() {
		List<ItemStack> itemList = new ArrayList<>(count); // Using list because I want to have remove(index)
		for (ItemStack item : items) {
			if (item == null) {
				continue;
			}
			itemList.add(item);
		}
		clear();
		return itemList;
	}
	
	public void deleteAll() {
		for (int i = 0; i < size; i++) {
			items[i] = null;
		}
	}
	
	public void addItemStack(ItemStack newStack) {
		assert !full() && newStack != null;
		if (getIndexOfId(newStack.getId()) != -1) {
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
