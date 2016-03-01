package com.git.cs309.mmoserver.items;

import java.io.Serializable;

import com.git.cs309.mmoserver.combat.CombatStyle;

public final class ItemDefinition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -506002191290787674L;
	private final int price;
	private final CombatStyle style;
	private final EquipmentSlot slot;
	private final int id;
	private final int strength;
	private final int defence;
	private final int level;
	private final String itemName;
	private final boolean stackable;
	
	public ItemDefinition(final String itemName, final int id, final int price, final int strength, final int defence, final int level, final CombatStyle style, final EquipmentSlot slot, final boolean stackable) {
		this.itemName = itemName;
		this.id = id;
		this.price = price;
		this.strength = strength;
		this.defence = defence;
		this.style = style;
		this.stackable = stackable;
		this.slot = slot;
		this.level = level;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @return the style
	 */
	public CombatStyle getStyle() {
		return style;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @return the defence
	 */
	public int getDefence() {
		return defence;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @return the stackable
	 */
	public boolean isStackable() {
		return stackable;
	}

	/**
	 * @return the slot
	 */
	public EquipmentSlot getSlot() {
		return slot;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
}
