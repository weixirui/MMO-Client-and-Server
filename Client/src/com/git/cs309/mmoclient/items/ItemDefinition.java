package com.git.cs309.mmoclient.items;

import java.io.Serializable;

public final class ItemDefinition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -506002191290787674L;
	private final int price;
	private final EquipmentSlot slot;
	private final int id;
	private final String itemName;
	
	public ItemDefinition(final String itemName, final int id, final int price, final EquipmentSlot slot) {
		this.itemName = itemName;
		this.id = id;
		this.price = price;
		this.slot = slot;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @return the slot
	 */
	public EquipmentSlot getSlot() {
		return slot;
	}
}
