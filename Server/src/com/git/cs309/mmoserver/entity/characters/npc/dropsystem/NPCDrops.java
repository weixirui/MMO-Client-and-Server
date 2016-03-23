package com.git.cs309.mmoserver.entity.characters.npc.dropsystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.git.cs309.mmoserver.items.ItemStack;

public final class NPCDrops {
	private static final Comparator<RateGroup> GROUP_COMPARATOR = new Comparator<RateGroup>() {

		@Override
		public int compare(RateGroup o1, RateGroup o2) {
			return (int) (o2.getRate() - o1.getRate());
		}
		
	};
	private final String npcName;
	private final List<RateGroup> groupList = new ArrayList<>();
	
	public NPCDrops(final String npcName, Collection<RateGroup> groups) {
		this.npcName = npcName;
		groupList.addAll(groups);
		groupList.sort(GROUP_COMPARATOR);
	}
	
	public List<ItemStack> getDrops() {
		List<ItemStack> drops = new ArrayList<>();
		for (RateGroup group : groupList) {
			if (!group.matchedRate((float) Math.random())) {
				continue;
			}
			drops.add(group.getDrop().createItemStack());
		}
		return drops;
	}

	/**
	 * @return the npcName
	 */
	public String getNpcName() {
		return npcName;
	}
}
