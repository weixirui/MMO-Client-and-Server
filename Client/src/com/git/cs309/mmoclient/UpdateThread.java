package com.git.cs309.mmoclient;

import javax.swing.JPanel;

import com.git.cs309.mmoclient.gui.*;

public class UpdateThread extends Thread{
	JPanel panel;
	JPanel tpanel;
	public UpdateThread(JPanel panel,JPanel tpanel) {
		this.panel = panel;
		this.tpanel = tpanel;
	}
	
	@Override
	public void run() {
		while(true){
			if(mainFrame.getTag()==1){
				panel.repaint();
			}else if(mainFrame.getTag()==2){
				tpanel.repaint();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
