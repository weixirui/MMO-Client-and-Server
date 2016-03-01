package com.git.cs309.mmoserver.entity.characters.npc.dropsystem;

public final class Range {
	private final int minimum;
	private final int maximum;
	
	public Range(final int minimum, final int maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}
	
	public int getAmount() {
		if (minimum == maximum) {
			return minimum;
		}
		return (int) (minimum + (Math.random() * (minimum - maximum)));
	}

	/**
	 * @return the minimum
	 */
	public int getMinimum() {
		return minimum;
	}

	/**
	 * @return the maximum
	 */
	public int getMaximum() {
		return maximum;
	}
}
