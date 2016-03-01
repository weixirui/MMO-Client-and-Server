package serverTest;



import java.awt.Graphics;

import com.git.cs309.mmoclient.gui.gameConfig;

/**
 * ��ɫ��
 * @author yy
 *
 */
public class Player extends Thread implements gameConfig{
	public static int px = panelX/2;
	public static int py = panelY/2;
	public static int x = 375;
	public static int y = 375;
	public static int mx = x+25;
	public static int my = y+25;
	static int step = 5;
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	public static int towards = 2;
	public static int up1 = 0;
	public static int down1 = 0;
	public static int left1 = 0;
	public static int right1 = 0;
	@Override
	public void run() {
		while(true){
			moveUD();
			moveLR();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	public void moveUD(){
		if(up){
			
			up1++;
			if(up1>=20){
				up1=0;
			}
			
			if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0||ReadMapFile.map3[y/elesize-1][x/elesize]!=0){
				int y1 = (y/elesize-1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y-step;
					my=my-step;
				}
			}else if(ReadMapFile.map2[y/elesize-1][x/elesize]==0&&ReadMapFile.map3[y/elesize-1][x/elesize]==0){//�Ϸ�û���壬���Լ��������ƶ�
				y=y-step;
				my=my-step;
			}
		}else if(down){
			down1++;
			if(down1>=20){
				down1=0;
			}
			if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0||ReadMapFile.map3[y/elesize+1][x/elesize]!=0){
				int y1 = (y/elesize+1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y+step;
					my=my+step;
				}
			}else if(ReadMapFile.map2[y/elesize+1][x/elesize]==0&&ReadMapFile.map3[y/elesize+1][x/elesize]==0){
				y=y+step;
				my=my+step;
			}
		}
	}
	

	public void moveLR(){
		if(left){
			left1++;
			if(left1>=20){
				left1=0;
			}
			if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0||ReadMapFile.map3[y/elesize][x/elesize-1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize-1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x-step;
					mx=mx-step;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize-1]==0&&ReadMapFile.map3[y/elesize][x/elesize-1]==0){
				x=x-step;
				mx=mx-step;
			}
		}else if(right){
			right1++;
			if(right1>=20){
				right1=0;
			}
			if(ReadMapFile.map2[y/elesize][x/elesize+1]!=0||ReadMapFile.map3[y/elesize][x/elesize+1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize+1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x+step;
					mx=mx+step;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize+1]==0&&ReadMapFile.map3[y/elesize][x/elesize+1]==0){
				x=x+step;
				mx=mx+step;
			}
		}
	}
	
	
	public static void draw(Graphics g){
		if(!up&&!down&&!left&&!right){
			if(towards==1){
				g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*3, 96, 96*4, null);
			}else if(towards==2){
				g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 0, 96, 96, null);
			}else if(towards==3){
				g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96, 96, 96*2, null);
			}else if(towards==4){
				g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*2, 96, 96*3, null);
			}
		}else{
			if(up){
				if(up1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*3, 96, 96*4, null);
				}else if(up1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*3, 96*2, 96*4, null);
				}else if(up1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96*3, 96*3, 96*4, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96*3, 96*4, 96*4, null);
				}
			}else if(down){
				if(down1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 0, 96, 96, null);
				}else if(down1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 0, 96*2, 96, null);
				}else if(down1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 0, 96*3, 96, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 0, 96*4, 96, null);
				}
			}else if(left){
				if(left1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96, 96, 96*2, null);
				}else if(left1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96, 96*2, 96*2, null);
				}else if(left1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96, 96*3, 96*2, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96, 96*4, 96*2, null);
				}
				
			}else if(right){
				if(right1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*2, 96, 96*3, null);
				}else if(right1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*2, 96*2, 96*3, null);
				}else if(right1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96*2, 96*3, 96*3, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96*2, 96*4, 96*3, null);
				}
			}
		}
	}
	
	
	
	public static int getI(){
		return (y-(playersize/2))/50;
	}
	
	public static int getJ(){
		return (x-(playersize/2))/50;
	}
}
