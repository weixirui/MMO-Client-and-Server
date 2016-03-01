package com.git.cs309.mmoserver.entity.characters.npc.dropsystem;

import java.util.ArrayList;
import java.util.List;

public final class RateGroup {
	private final Rate rate;
	private final List<Drop> drops = new ArrayList<>();
	
	public RateGroup(final Rate rate) {
		this.rate = rate;
	}
	
	public String getRateName() {
		return rate.getName();
	}
	
	public boolean empty() {
		return drops.size() == 0;
	}
	
	public void addToGroup(Drop drop) {
		drops.add(drop);
	}
	
	public float getRate() {
		return rate.getPercentChance() * drops.size();
	}
	
	public boolean matchedRate(float random) {
		return (rate.getPercentChance() * drops.size()) > random;
	}
	
	public Drop getDrop() {
		return drops.get((int) (Math.random() * drops.size()));
	}
}
