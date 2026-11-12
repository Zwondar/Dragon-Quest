package tool;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import frame.Startgame;
import image.getimage;

public class Slime {

	public static int HP=3;
	
	public  BufferedImage showpicture=getimage.slimeleft1;
	
	public int SlimeHp=HP;
	
	public int x;
	
	public int y=Startgame.height/2+Startgame.height/7;
	
	public int width=Startgame.width/10;
	
	public int height=Startgame.height/7;
	
	public  boolean attackcontrol=true;
	
	slimeright sr=new slimeright(this);
	
	slimeleft sl=new slimeleft(this);
	
	 int distance=Math.abs(Math.abs(x)-Math.abs(Warrior.x));
	 
	 public boolean movecontrol=true;
	 
	 public Slime(int x) {
		 this.x=x;
	 }
	
	public void paintself(Graphics g) {
		
		machine();
		
		g.drawImage(showpicture,x,y,width,height, null);
		
	}
	
	 public Rectangle getRectangleMonster() {
			
			return new Rectangle(x,y,width,height);
		}
	 
	
	
	public  void machine() {
		// 2026-11-09 修复（优先级6）：distance 原在字段初始化时用 x=0 计算且从此不再更新，
		// 导致 machine() 里的距离判断形同虚设；这里每次判断前重新计算与玩家的距离。
		distance = Math.abs(Math.abs(x) - Math.abs(Warrior.x));
		if(distance>=50) {
			if(x>Warrior.x&&movecontrol==true) {
				sl.play();
				movecontrol=false;
			}
			else if(x<=Warrior.x&&movecontrol==false)	{
				sr.play();
				movecontrol=true;
			}
			
		}
	}
	
}

class slimeright implements Runnable{
private Thread thread;
	Slime s;
public slimeright(Slime s) {
	this.s=s;
}

	public void run() {
		int index=0;
		while(true) {
			
			if(index==8) index=0;
			
			if(s.x>Warrior.x||s.SlimeHp<=0) break;
			
			s.showpicture=getimage.slimeright.get(index);
			//改变参数
			if(s.x<=Warrior.x-70) {
				s.x+=10;
			}
			
			if(s.attackcontrol==false&&index==5) {
				s.attackcontrol=true;
			}
			
			
			index++;
			try {
				thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
}

class slimeleft implements Runnable{
private Thread thread;
Slime s;
public slimeleft(Slime s) {
	this.s=s;
}

	public void run() {
		
		int index=0;
		while(true) {
			
			if(index==8) index=0;
			
			if(s.x<=Warrior.x||s.SlimeHp<=0) break;
			
			s.showpicture=getimage.slimeleft.get(index);
			
			
			if(s.attackcontrol==false&&index==5) {
				s.attackcontrol=true;
			}
			//改变参数
			if(s.x>=Warrior.x+60) {
				s.x-=10;
			}
			
			index++;
			try {
				thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
}

