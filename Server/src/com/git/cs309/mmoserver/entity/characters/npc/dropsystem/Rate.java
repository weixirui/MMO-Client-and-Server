package com.git.cs309.mmoserver.entity.characters.npc.dropsystem;

public final class Rate {
	private final float percentChance;
	private final String name;
	
	public Rate(final String name, final float percentChance) {
		this.percentChance = percentChance;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Rate)) {
			return false;
		}
		return ((Rate) other).percentChance == percentChance;
	}

	/**
	 * @return the percentChance
	 */
	public float getPercentChance() {
		return percentChance;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
