package serverTest;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class ReadMapFile {
	public static int[][] map1;
	public static int[][] map2;
	public static int[][] map3;
	
	static void readfile(String path){
		try{
			FileInputStream fis = new FileInputStream(path);
			DataInputStream dis = new DataInputStream(fis);
			int i = dis.readInt();
			int j = dis.readInt();
			map1 = new int[i][j];
			map2 = new int[i][j];
			map3 = new int[i][j];
			for(int ii=0;ii<i;ii++){
				for(int jj=0;jj<j;jj++){
					map1[ii][jj] = dis.readInt();
					map2[ii][jj] = dis.readInt();
					map3[ii][jj] = dis.readInt();
				}
			}
			dis.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

