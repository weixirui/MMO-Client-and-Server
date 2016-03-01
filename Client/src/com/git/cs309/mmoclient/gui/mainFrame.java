package com.git.cs309.mmoclient.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import map.GetMap;

import com.git.cs309.mmoclient.UpdateThread;
import com.git.cs309.mmoclient.NPC.GetNPC;

import serverTest.Player;
import serverTest.ReadMapFile;

public class mainFrame extends JFrame implements gameConfig{
	static int tag = 1;
	JPanel panel;
	JPanel tpanel;
	
	public mainFrame() {
		init();
		
	}
	
	
	public void init(){
		this.setTitle(title);
		this.setSize(frameX, frameY);
		this.setLayout(null);
		this.setDefaultCloseOperation(3);

		tpanel = settpanel();
		panel = setpanel();
		
		this.add(tpanel);
		this.add(panel);
		
		
		this.setVisible(true);
		
		PanelListenner plis = new PanelListenner();
		this.addKeyListener(plis);
		
		
		Player player = new Player();
		player.start();
		
		
		UpdateThread ut = new UpdateThread(panel,tpanel);
		ut.start();
	}
	
	public JPanel setpanel(){
		JPanel panel = new MyPanel();
		panel.setBounds(18, 5, panelX, panelY);
		panel.setLayout(null);
		panel.setBackground(Color.black);
		
		return panel;
	}
	
	public JPanel settpanel(){
		JPanel tpanel = new TalkPanel();
		return tpanel;
	}
	
	
	class PanelListenner extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
			if(tag==1){
				switch (code) {
				case KeyEvent.VK_UP:
					Player.up = true;
					Player.towards = 1;
					break;
				case KeyEvent.VK_DOWN:
					Player.down = true;
					Player.towards = 2;
					break;
				case KeyEvent.VK_LEFT:
					Player.left = true;
					Player.towards = 3;
					break;
				case KeyEvent.VK_RIGHT:
					Player.right = true;
					Player.towards = 4;
					break;
				case KeyEvent.VK_G:
					if(Player.towards==1){
						int num = ReadMapFile.map3[Player.y/elesize-1][Player.x/elesize];
						if(num!=0){
							if(GetNPC.map.get(num)==null){
								GetNPC.getnpc(num);
								TalkPanel.gettalknpc(num);
							}
							tag = 2;
							tpanel.setBounds(28, 500, 630, 150);
							
							tpanel.repaint();
							System.out.println(1);
						}
					}else if(Player.towards==2){
						if(ReadMapFile.map3[Player.y/elesize+1][Player.x/elesize]!=0){
							tpanel.setBounds(28, 500, 630, 150);
							tag = 2;
							tpanel.repaint();
						}
					}else if(Player.towards==3){
						if(ReadMapFile.map3[Player.y/elesize][Player.x/elesize-1]!=0){
							tpanel.setBounds(28, 500, 630, 150);
							tag = 2;
							tpanel.repaint();
						}
					}else if(Player.towards==4){
						if(ReadMapFile.map3[Player.y/elesize][Player.x/elesize+1]!=0){
							tpanel.setBounds(28, 500, 630, 150);
							tag = 2;
							tpanel.repaint();
						}
					}
					
					break;

				default:
					break;
				}
			}else if(tag==2){
				if(code==KeyEvent.VK_G){
					tag=1;
					tpanel.setBounds(28, 500, 0, 0);
				}
			}
			
		}
		
		public void keyReleased(KeyEvent e){
			if(tag==1){
				int code = e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
					Player.up = false;
					Player.up1 = 0;
					break;
				case KeyEvent.VK_DOWN:
					Player.down = false;
					Player.down1 = 0;
					break;
				case KeyEvent.VK_LEFT:
					Player.left = false;
					Player.left1 = 0;
					break;
				case KeyEvent.VK_RIGHT:
					Player.right = false;
					Player.right1 = 0;
					break;

				default:
					break;
				}
			}
		}
	}
	
	class MyPanel extends JPanel{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			for(int i=Player.getI()-6;i<=Player.getI()+6;i++){
				for(int j=Player.getJ()-6;j<=Player.getJ()+6;j++){
					
					if(i>=0&&j>=0&&i<ReadMapFile.map1.length&&j<ReadMapFile.map1[0].length){
						
						ImageIcon icon = GetMap.int2icon(ReadMapFile.map1[i][j]);
						g.drawImage(icon.getImage(), (Player.px-elesize/2)+((j-Player.getJ())*elesize)-(Player.mx%elesize), (Player.py-elesize/2)+((i-Player.getI())*elesize)-(Player.my%elesize), elesize, elesize, null);
						
						if(ReadMapFile.map2[i][j]!=0){
							ImageIcon icon2 = GetMap.int2icon(ReadMapFile.map2[i][j]);
							g.drawImage(icon2.getImage(), (Player.px-elesize/2)+((j-Player.getJ())*elesize)-(Player.mx%elesize), (Player.py-elesize/2)+((i-Player.getI())*elesize)-(Player.my%elesize), elesize, elesize, null);
						}
						
						if(ReadMapFile.map3[i][j]!=0){
							ImageIcon icon3 = GetMap.int2npc(ReadMapFile.map3[i][j]);
							g.drawImage(icon3.getImage(), (Player.px-elesize/2)+((j-Player.getJ())*elesize)-(Player.mx%elesize), (Player.py-elesize/2)+((i-Player.getI())*elesize)-(Player.my%elesize), elesize, elesize, null);
						}
						
					}
				}
			}
			
			Player.draw(g);
			
			
			g.drawImage(shadow2.getImage(), 0, 0, 650, 650, null);
		}
	}
	public static int getTag() {
		return tag;
	}
}
