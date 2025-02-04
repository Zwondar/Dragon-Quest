package tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.Thread.State;

import Sound.soundplayer;
import frame.Startgame;
import image.getimage;

public class Warrior {
	
	public static int WarriorHP=50;//血量
	
	public static int WarriorMana=12;//法力值
	
	attack a=new attack();
	
	dead d=new dead();
	
	right r=new right();
	
	left l=new left();
	
	jump j=new jump();
	
	Obstale o=new Obstale();
	
	over ov=new over();
	
	Monster m;
	
public static BufferedImage showpicture=getimage.warriorstandright;//创建人物展示图片

public static int speed=Startgame.width/112;//战士移动速度

public static int jumpspeed=Startgame.height/192;//战士跳跃速度

public static int x=500;//战士的初始坐标x

public static int y=Startgame.height/2;//战士的初始坐标y

public static boolean movecontrol=true;//控制人物移动

public static boolean jumpcontrol=true;//控制人物跳跃

public static boolean impact=true;

public static boolean attackcontrol=true;

public static boolean dead=true;

public static boolean deadcontrol=false;

public static boolean overcontrol=false;
 
	public void paintself(Graphics g) {//绘制战士
	
if(Warrior.WarriorHP<=0&&dead==true) {
			d.play();
			dead=false;
		}else if(Warrior.WarriorHP>0){
			
			if(Obstale.treeHP<=0&&overcontrol==false) {
				
				ov.play();
				
				overcontrol=true;
				
			}
			
			if(impact==true&&o.getRectangletree().intersects(getRectangleWarrior())) {
				o.treeHP--;
				impact=false;
			}
			for(int i=0;i<getimage.monster.size();i++) {
			if(impact==true&&getimage.monster.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
				
					getimage.monster.get(i).MinsterHp--;
				
				impact=false;
			}
			}
			for(int i=0;i<getimage.slime.size();i++) {
				if(impact==true&&getimage.slime.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
					
						getimage.slime.get(i).SlimeHp--;
					
					impact=false;
				}
			}
				for(int i=0;i<getimage.red.size();i++) {
					if(impact==true&&getimage.red.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
						
							getimage.red.get(i).MinsterHp--;
						
						impact=false;
					}
				}
					for(int i=0;i<getimage.green.size();i++) {
						if(impact==true&&getimage.green.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
							
								getimage.green.get(i).MinsterHp--;
							
							impact=false;
						}
				}
			for(int i=0;i<getimage.monster.size();i++) {
			if(getimage.monster.get(i).attackcontrol==true&&getimage.monster.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
				WarriorHP--;
				getimage.monster.get(i).attackcontrol=false;
			}
			}
			for(int i=0;i<getimage.slime.size();i++) {
				if(getimage.slime.get(i).attackcontrol==true&&getimage.slime.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
					WarriorHP--;
					getimage.slime.get(i).attackcontrol=false;
				}
				}
			for(int i=0;i<getimage.red.size();i++) {
				if(getimage.red.get(i).attackcontrol==true&&getimage.red.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
					WarriorHP--;
					getimage.red.get(i).attackcontrol=false;
				}
				}
			for(int i=0;i<getimage.green.size();i++) {
				if(getimage.green.get(i).attackcontrol==true&&getimage.green.get(i).getRectangleMonster().intersects(getRectangleWarrior())) {
					WarriorHP--;
					getimage.green.get(i).attackcontrol=false;
				}
				}

			
			
			
			if(Startgame.attackstatus.equals("attack")&&attackcontrol==true) {
				a.play();
				attackcontrol=false;
			}
		
			if(Startgame.status.equals("right")&&movecontrol==true) {
				r.play();
				movecontrol=false;
			}
		//使人物向右走
			
			if(Startgame.status.equals("left")&&movecontrol==true) {
				l.play();
				movecontrol=false;
			}
			//使人物向左走
			if(Startgame.jumpstatus.equals("jump")&&jumpcontrol==true) {
				j.play();
				jumpcontrol=false;
			}
			//使人物向上跳
			
		}
		
		
		g.drawImage(showpicture, x, y,Startgame.width/15,Startgame.height/3, null);//进行战士的绘制
		/*
		 * 设置血量和法力值
		 * 
		 * */
		g.setColor(Color.RED);
		
		g.setFont(new Font("宋体", Font.BOLD, 60));
		
		g.drawString("血量    ："+String.valueOf(WarriorHP),20,100);
		
		g.drawString("法力值    ："+String.valueOf(WarriorMana),20,200);
		
	}
	
	public Rectangle getRectangleWarrior() {
		
		return new Rectangle(x,y,Startgame.width/15,Startgame.height/3);
	}
	
}

	
	
	


class dead implements Runnable {
public static Thread thread;
over o=new over();
	@Override
	public void run() {
		
		soundplayer.dead();
		
		getimage.red.clear();
		
		getimage.green.clear();
		
		getimage.monster.clear();
		
		getimage.slime.clear();
		
		Warrior.deadcontrol=true;
     
		for(int i=0;i<3;i++) {
			
			Warrior.showpicture=getimage.rightdead.get(i);
			
			
			try {
				thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
	//还原游戏数据初始化状态
		
		Warrior.deadcontrol=false;
		o.play();
		
	}
	
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
}

class attack implements Runnable{
 
	public static Thread thread;

	@Override
	public void run() {
		

		if(Startgame.jumpstatus.equals("jump")==false&&Startgame.attackstatus.equals("attack")) {
			int index=0;
			
			while(true) {
				
				if(index==1) {
					
				soundplayer.attack();
				}
				if(Warrior.deadcontrol==true) break;
				
				if(Startgame.attackstatus.equals("stand")) break;
				
				if(index==6) {
					index=0;
				}
				if(Startgame.status.equals("left")) Warrior.showpicture=getimage.leftattack.get(index);
				
				else Warrior.showpicture=getimage.rightattack.get(index);
				
				if(index==5) Warrior.impact=true;
				
				else Warrior.impact=false;
					
				try {
					
					thread.sleep(100);//图片切换速度
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				index++;
				
			}
			
		}
		Warrior.attackcontrol=true;
		
	}
	
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
	
}

class right implements Runnable{//向右走的线程
	
private static Thread thread;//用于启动线程
	@Override
	public void run() {//控制战士向右走
		
		int index=0;//初始索引
		
		while(true) {
			
			if(index==1&&Startgame.jumpstatus.equals("jump")==false) {
				soundplayer.walk();
			}
			
			if(Warrior.deadcontrol==true) break;
			
			if(Startgame.status.equals("right")==false) break;//如果status！=right则退出循环
			
			if(index==8) {
				index=0;
			}
			if(Startgame.jumpstatus.equals("jump")==false&&Startgame.attackstatus.equals("stand")) Warrior.showpicture=getimage.right.get(index);//循环切换图片
			else if(Startgame.attackstatus.equals("stand"))Warrior.showpicture=getimage.warriorstandright;
			
			if(Warrior.x<=Startgame.width-Startgame.width/9) {//判断是否带动背景移动
				if(Startgame.jumpstatus.equals("jump")) Warrior.x+=Warrior.speed/5;
				else if(Startgame.attackstatus.equals("attack")) Warrior.x+=Warrior.speed/5;
				else Warrior.x+=Warrior.speed;
				backgrand.backgrandmoveright=false;
			}else {
				backgrand.backgrandmoveright=true;
			}
			
			try {
				if(Startgame.jumpstatus.equals("jump")||Startgame.attackstatus.equals("attack")) thread.sleep(10);
				else thread.sleep(100);//图片切换速度
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			index++;
		
		}
		backgrand.backgrandmoveright=false;
		Warrior.movecontrol=true;
		Warrior.showpicture=getimage.warriorstandright;
	}
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
}

class left implements Runnable{//向右走的线程
	
private static Thread thread;//用于启动线程
	@Override
	public void run() {//控制战士向右走
		
		int index=0;//初始索引
		
		while(true) {
			
			if(index==1&&Startgame.jumpstatus.equals("jump")==false) {
				soundplayer.walk();
			}
			
			if(Warrior.deadcontrol==true) break;
			
			if(Startgame.status.equals("left")==false) break;//如果status！=right则退出循环
			
			if(index==8) {
				index=0;
			}
			if(Startgame.jumpstatus.equals("jump")==false&&Startgame.attackstatus.equals("stand")) Warrior.showpicture=getimage.left.get(index);
			else if(Startgame.attackstatus.equals("stand")) Warrior.showpicture=getimage.warriorstandleft;
			
			
			if(Warrior.x>=Startgame.width/9) {
				if(Startgame.jumpstatus.equals("jump")) Warrior.x-=Warrior.speed/5;
				else if(Startgame.attackstatus.equals("attack")) Warrior.x-=Warrior.speed/5;
				else Warrior.x-=Warrior.speed;
				backgrand.backgrandmoveleft=false;
			}else {
				backgrand.backgrandmoveleft=true;
			}
			
			try {
				if(Startgame.jumpstatus.equals("jump")||Startgame.attackstatus.equals("attack")) thread.sleep(10);
				else thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			index++;
		
		}
		backgrand.backgrandmoveleft=false;
		Warrior.movecontrol=true;
		Warrior.showpicture=getimage.warriorstandleft;
	}
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
}
class jump implements Runnable{
	
private static Thread thread;
	@Override
	public void run() {
		
		soundplayer.jump();
		
		while(true) {
			
			if(Warrior.deadcontrol==true) break;
			
		try {
			thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
			if(Startgame.jumpstatus.equals("jump")&&Warrior.y>=Startgame.height/5) Warrior.y-=Startgame.height/160;
			else break;
		}
		while(true) {
			try {
				thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			Warrior.y+=Startgame.height/160;
			if(Warrior.y==Startgame.height/2) {
				break;
			}
		}
		Startgame.jumpstatus="stand";
		Warrior.jumpcontrol=true;
	}
	
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
	
}
