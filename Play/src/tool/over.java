package tool;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import frame.Startgame;
import image.getimage;

public class over implements Runnable{
	
static Thread thread;

public static int x=0;

public static int y=0;

public static int width=Startgame.width;

public static int height=Startgame.height;

public static boolean control=false;

public static BufferedImage showpicture=getimage.gameover;

public void paintself(Graphics g) {
	
	g.drawImage(showpicture, x, y,width,height,null);
	
}

	@Override
	public void run() {
		control=true;
	if(Obstale.treeHP<=0) {
		
		getimage.red.clear();
		
		getimage.green.clear();
		
       getimage.monster.clear();
		
		getimage.slime.clear();
		
		showpicture=getimage.win;
		
		width=Startgame.width;
		
		height=Startgame.height/2;
		
		while(true) {
			
			try {
				thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
			if(y>=Startgame.height/2) break;
			
			y++;
			
		}
		x=0;
		y=0;
       Startgame.checkpoint=0;
   	   Obstale.treeHP=7;
		Obstale.x2=Startgame.width*2-Startgame.width/5;
		backgrand.x=0;
		Warrior.WarriorHP=45;
		Warrior.x=0;
		Warrior.y=Startgame.height/2;
		Warrior.overcontrol=false;
		
	}else if(Warrior.WarriorHP<=0) {
		
		width=Startgame.width;
		
		height=Startgame.height;
		
		showpicture=getimage.gameover;
		
		try {
			thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		Startgame.checkpoint=0;
		x=0;
		y=0;
		Obstale.treeHP=7;
		Warrior.WarriorHP=45;
		Warrior.x=0;
		backgrand.x=0;
		Obstale.x2=Startgame.width*2-Startgame.width/5;
		Warrior.y=Startgame.height/2;
		Warrior.dead=true;
		
	}
	control=false;
	}
	public void play() {
		thread=new Thread(this);
		thread.start();
	}

}
