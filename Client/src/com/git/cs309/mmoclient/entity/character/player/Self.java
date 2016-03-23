package com.git.cs309.mmoclient.entity.character.player;

import com.git.cs309.mmoclient.Client;
import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoclient.gui.game.ViewPanel;
import com.git.cs309.mmoclient.items.ItemContainer;
import com.git.cs309.mmoserver.packets.ExtensivePlayerCharacterPacket;

public class Self extends PlayerCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3676492298604751040L;
	
	private ItemContainer inventory = new ItemContainer(40);
	private Equipment equipment = new Equipment();

	public Self(ExtensivePlayerCharacterPacket packet) {
		super(packet);
		assert packet.getUniqueID() == Client.getSelfId();
	}
	
	public ItemContainer getInventory() {
		return inventory;
	}
	
	public Equipment getEquipment() {
		return equipment;
	}
	
	@Override
	public int getPaintX() {
		return (ViewPanel.getInstance().getWidth() / 2) - (Config.DEFAULT_SPRITE_WIDTH / 2);
	}
	
	public int getPaintY() {
		return (ViewPanel.getInstance().getHeight() / 2) - (Config.DEFAULT_SPRITE_HEIGHT / 2);
	}

}
