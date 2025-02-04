package tool;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Sound.soundplayer;
import frame.Startgame;
import image.getimage;

public class Monster {
	
	public static int HP=5;

	public  int MinsterHp=HP;
	
	public  BufferedImage showpicture=getimage.monsterrunright3;
	
	public  int y=Startgame.height/2;
	
	public   int  x;
	
	 int distance=Math.abs(Math.abs(x)-Math.abs(Warrior.x));
	 
	 monright mr=new monright(this);
	 
	 monleft ml=new monleft(this);
	 
	 mondead md=new mondead(this);
	 
	 public  boolean movecontrol=true;
	 
	 public  boolean attackcontrol=true;
	 
	 public boolean deadcontrol=true;
	 
	 public boolean over=true;//控制死亡动画
	 
	
	 public Monster(int x) {
		 this.x=x;
	 }
	 
	
	 public void paintself(Graphics g) {
		 
		 machine();
		 
		 if(deadcontrol==true&&MinsterHp<=0) {
			 md.play();
			 deadcontrol=false;
		 }
		
			 g.drawImage(showpicture, x, y,Startgame.width/8,Startgame.height/3, null);
		 
	      
		
		 
		 
	 }
	 
	 public Rectangle getRectangleMonster() {
		
			return new Rectangle(x,y+Startgame.height/20,Startgame.width/9,Startgame.height/3);
		}
	 
	 
	public  void machine() {
		if(distance>=50) {
			if(x>Warrior.x&&movecontrol==true) {
				ml.play();
				movecontrol=false;
			}
			else if(x<=Warrior.x&&movecontrol==false)	{
				mr.play();
				movecontrol=true;
			}
			
		}
	}
}

class mondead implements Runnable{
static Thread thread;
Monster m;

public mondead(Monster m) {
	this.m=m;
}
	
	public void run() {
	
		soundplayer.mondead();
		
	if(m.x>=Warrior.x) {
		for(int i=0;i<2;i++) {
			m.showpicture=getimage.monsterdeadleft.get(i);
			
			try {
				thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}else {
		
		for(int i=0;i<2;i++) {
			m.showpicture=getimage.monsterdeadright.get(i);
			
			try {
				thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	m.over=false;
	}
	
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
}

class monright implements Runnable{
static Thread thread;
	Monster m;
public monright(Monster m) {
	this.m=m;
}

	public void run() {
		int index=0;
		while(true) {
			
			if(index==7) index=0;
			
			if(m.x>Warrior.x||m.MinsterHp<=0) break;
			
			m.showpicture=getimage.monsterrunright.get(index);
			
			if(m.x<=Warrior.x-130) {
				m.x+=12;
			}
			
			if(m.attackcontrol==false&&index==5) {
				m.attackcontrol=true;
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

class monleft implements Runnable{
static Thread thread;
Monster m;
public monleft(Monster m) {
	this.m=m;
}

	public void run() {
		int index=0;
		while(true) {
			
			if(index==7) index=0;
			
			if(m.x<=Warrior.x||m.MinsterHp<=0) break;
			
			m.showpicture=getimage.monsterrunleft.get(index);
			
			
			if(m.attackcontrol==false&&index==5) {
				m.attackcontrol=true;
			}
			
			if(m.x>=Warrior.x+75) {
				m.x-=12;
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
