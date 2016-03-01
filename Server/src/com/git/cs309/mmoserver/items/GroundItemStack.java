package com.git.cs309.mmoserver.items;

import java.util.ArrayList;
import java.util.List;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.Main;
import com.git.cs309.mmoserver.cycle.CycleProcess;
import com.git.cs309.mmoserver.cycle.CycleProcessManager;
import com.git.cs309.mmoserver.map.Map;

public final class GroundItemStack {
	private final List<ItemStack> groundItems;
	private final int x, y;
	private final Map map;

	public GroundItemStack(int x, int y, Map map) {
		groundItems = new ArrayList<ItemStack>();
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
