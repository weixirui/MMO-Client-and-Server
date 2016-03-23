package com.git.cs309.mmoclient.gui.characterselection;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.git.cs309.mmoclient.Client;
import com.git.cs309.mmoserver.packets.CharacterSelectionDataPacket;
import com.git.cs309.mmoserver.packets.InterfaceClickPacket;

public class SelectionCharacterComponent extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3409845224182388202L;
	private final SelectionCharacter character;
	private final int index;
	
	public SelectionCharacterComponent(int index) {
		this.index = index;
		character = new SelectionCharacter(index);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				switch (index) {
				case 0:
					Client.getConnection().addOutgoingPacket(new InterfaceClickPacket(null, InterfaceClickPacket.CHARACTER_1_SLOT, 0, 0, 0));
					break;
				case 1:
					Client.getConnection().addOutgoingPacket(new InterfaceClickPacket(null, InterfaceClickPacket.CHARACTER_2_SLOT, 0, 0, 0));
					break;
				case 2:
					Client.getConnection().addOutgoingPacket(new InterfaceClickPacket(null, InterfaceClickPacket.CHARACTER_3_SLOT, 0, 0, 0));
					break;
				case 3:
					Client.getConnection().addOutgoingPacket(new InterfaceClickPacket(null, InterfaceClickPacket.CHARACTER_4_SLOT, 0, 0, 0));
					break;
				case 4:
					Client.getConnection().addOutgoingPacket(new InterfaceClickPacket(null, InterfaceClickPacket.CHARACTER_5_SLOT, 0, 0, 0));
					break;
				}
			}
		});
	}
	
	public void updateSelectionCharacter(CharacterSelectionDataPacket packet) {
		character.setCharacter(packet);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth(), getHeight());
		if (character.hasCharacter()) {
			g.drawString(character.getName(), getWidth() / 4, getHeight() / 4 );
		}
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
}
