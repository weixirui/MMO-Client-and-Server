package com.git.cs309.mmoclient.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.git.cs309.mmoclient.NPC.GetNPC;

import serverTest.NPC;

public class TalkPanel extends JPanel implements gameConfig{
	static NPC npc;
	
	
	public TalkPanel() {
		init();
	}
	
	public void init(){
		this.setBounds(28, 500, 0, 0);
		this.setLayout(null);
		this.setOpaque(false);
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(npc!=null){
			g.drawImage(talkbox.getImage(), 0, 0, 630, 130, null);
			g.setColor(Color.BLUE);
			Font font = new Font("aaaa", 600, 25);
			g.setFont(font);
			g.drawString(npc.getNPCName()+":", 30, 30);
			g.setColor(Color.GREEN);
			g.drawString(npc.getNPCTalk(), 60, 65);
		}
	}
	
	public void show(){
		this.setPreferredSize(new Dimension(panelX, panelY));
	}
	
	public void hide(){
		this.setPreferredSize(new Dimension(0, 0));
	}
	
	public static void gettalknpc(int num){
		npc = GetNPC.map.get(num);
	}
	
}
