package tool;

import java.awt.Graphics;
import java.awt.Rectangle;

import frame.Startgame;
import image.getimage;

public class Obstale {
	
	
	public static int treeHP=7;//血量

public static int x2=Startgame.width*2-Startgame.width/5;//树桩障碍的坐标x
	
	public void paintself(Graphics g) {
		
g.drawImage(getimage.obstaletree, x2, Startgame.height/2,Startgame.width/5,Startgame.height/3, null);
		
	
	}
	
	public  Rectangle getRectangletree() {
		
		return new Rectangle(x2,Startgame.height/19,Startgame.width/5,Startgame.height/2+Startgame.height/3-Startgame.height/19);
	}
	
}
