package com.git.cs309.mmoclient.gui;

import javax.swing.ImageIcon;

public interface gameConfig {
	String title = "MMO";
	int frameX = 700;
	int frameY = 700;
	int panelX = 650;
	int panelY = 650;
	int elesize = 50;
	int playersize = 50;
	ImageIcon icon0 = new ImageIcon("000.png");
	ImageIcon icon1 = new ImageIcon("grass.png");
	ImageIcon icon2 = new ImageIcon("ground.png");
	ImageIcon icon3 = new ImageIcon("blackground.png");
	ImageIcon icon100 = new ImageIcon("redtree.png");
	ImageIcon icon101 = new ImageIcon("greentree.png");
	ImageIcon icon102 = new ImageIcon("greentree2.png");
	ImageIcon icon103 = new ImageIcon("highgreentree.png");
	ImageIcon icon150 = new ImageIcon("redground.png");
	
	ImageIcon walk = new ImageIcon("playerrun.png");
	ImageIcon walk1 = new ImageIcon("playerStop.png");
	
	ImageIcon npc201 = new ImageIcon("NPC.png");
	
	ImageIcon shadow2 = new ImageIcon("black2.png");
	
	ImageIcon talkbox = new ImageIcon("talk.png");
	
}

