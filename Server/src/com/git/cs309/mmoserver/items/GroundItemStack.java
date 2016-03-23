package com.git.cs309.mmoserver.items;

import java.util.ArrayList;
import java.util.List;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.cycle.CycleProcess;
import com.git.cs309.mmoserver.cycle.CycleProcessManager;
import com.git.cs309.mmoserver.entity.Entity;
import com.git.cs309.mmoserver.entity.EntityType;
import com.git.cs309.mmoserver.map.Map;
import com.git.cs309.mmoserver.packets.GroundItemsPacket;
import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.util.ClosedIDSystem;

public final class GroundItemStack extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2111922970196910069L;
	private final List<ItemStack> groundItems;
	private final Map map;

	public GroundItemStack(int x, int y, Map map) {
		super (x, y, map.getZ(), ClosedIDSystem.getTag(), Entity.GROUND_ITEM_STATIC_ID, "GroundItemStack");
		groundItems = new ArrayList<ItemStack>();
		this.instanceNumber = map.getInstanceNumber();
		this.x = x;
		this.y = y;
		this.map = map;
	}

	public void addItemStack(ItemStack stack) {
		groundItems.add(stack);
		CycleProcessManager.getInstance().addProcess(new CycleProcess() {
			final long start = Main.getTickCount();

			@Override
			public void end() {
				groundItems.remove(stack);
				map.itemStackChanged(x, y);
			}

			@Override
			public boolean finished() {
				return Main.getTickCount() - start >= Config.TICKS_TILL_ITEM_DESPAWN;
			}

			@Override
			public void process() {

			}

		});
		map.itemStackChanged(x, y);
	}

	public void addItemStack(ItemStack stack, boolean despawns) {
		groundItems.add(stack);
		if (despawns)
			CycleProcessManager.getInstance().addProcess(new CycleProcess() {
				final long start = Main.getTickCount();

				@Override
				public void end() {
					groundItems.remove(stack);
					map.itemStackChanged(x, y);
				}

				@Override
				public boolean finished() {
					return Main.getTickCount() - start >= Config.TICKS_TILL_ITEM_DESPAWN;
				}

				@Override
				public void process() {

				}

			});
		map.itemStackChanged(x, y);
	}

	public ItemStack getStack(int index) {
		assert index < groundItems.size() && index >= 0;
		return groundItems.get(index);
	}

	public ItemStack removeStack(int index) {
		assert index < groundItems.size() && index >= 0;
		ItemStack stack = groundItems.remove(index);
		map.itemStackChanged(x, y);
		return stack;
	}

	public List<ItemStack> getStack() {
		return groundItems;
	}

	public int getStackSize() {
		return groundItems.size();
	}

	@Override
	public boolean canWalkThrough() {
		return true;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.GROUND_ITEM;
	}

	@Override
	public Packet getExtensivePacket() {
		int[] itemIds = new int[groundItems.size()];
		for (int i = 0; i < groundItems.size(); i++) {
			itemIds[i] = groundItems.get(i).getId();
		}
		return new GroundItemsPacket(null, getX(), getY(), itemIds);
	}
}
