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

// 2026-11-09 修复（优先级6）：记录人物朝向（right/left）。
// 攻击动画在松手后仍要完整播放，结束时需要据此恢复正确的站立帧。
public static String facing="right";

public static int speed=Startgame.width/112;//战士移动速度

public static int jumpspeed=Startgame.height/192;//战士跳跃速度

public static int x=500;//战士的初始坐标x

public static int y=Startgame.height/2;//战士的初始坐标y

public static boolean movecontrol=true;//控制人物移动

public static boolean jumpcontrol=true;//控制人物跳跃

// 2026-11-09 修复（优先级3）：初始值改为 false。
// 原来为 true，导致开局第一次碰到怪物/树时会"白嫖"一次伤害；现在只有攻击动画第 5 帧才置 true。
public static boolean impact=false;

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
			
			// 2026-11-09 修复（优先级3）：所有接触判定都要求目标血量 > 0，
			// 避免已经死亡、看不见的怪物/史莱姆继续扣血；
			// 同时把按下标遍历改为增强 for，兼容线程安全的 CopyOnWriteArrayList。
			if(impact==true&&o.getRectangletree().intersects(getRectangleWarrior())) {
				o.treeHP--;
				impact=false;
			}
			for (Monster m : getimage.monster) {
				if (m.MinsterHp > 0 && impact==true && m.getRectangleMonster().intersects(getRectangleWarrior())) {
					m.MinsterHp--;
					impact=false;
				}
			}
			for (Slime s : getimage.slime) {
				if (s.SlimeHp > 0 && impact==true && s.getRectangleMonster().intersects(getRectangleWarrior())) {
					s.SlimeHp--;
					impact=false;
				}
			}
			for (Monster3 m : getimage.red) {
				if (m.MinsterHp > 0 && impact==true && m.getRectangleMonster().intersects(getRectangleWarrior())) {
					m.MinsterHp--;
					impact=false;
				}
			}
			for (Monster2 m : getimage.green) {
				if (m.MinsterHp > 0 && impact==true && m.getRectangleMonster().intersects(getRectangleWarrior())) {
					m.MinsterHp--;
					impact=false;
				}
			}

			// 2026-11-09 修复（优先级3）：怪物反击同样要求血量 > 0。
			for (Monster m : getimage.monster) {
				if (m.MinsterHp > 0 && m.attackcontrol==true && m.getRectangleMonster().intersects(getRectangleWarrior())) {
					WarriorHP--;
					m.attackcontrol=false;
				}
			}
			for (Slime s : getimage.slime) {
				if (s.SlimeHp > 0 && s.attackcontrol==true && s.getRectangleMonster().intersects(getRectangleWarrior())) {
					WarriorHP--;
					s.attackcontrol=false;
				}
			}
			for (Monster3 m : getimage.red) {
				if (m.MinsterHp > 0 && m.attackcontrol==true && m.getRectangleMonster().intersects(getRectangleWarrior())) {
					WarriorHP--;
					m.attackcontrol=false;
				}
			}
			for (Monster2 m : getimage.green) {
				if (m.MinsterHp > 0 && m.attackcontrol==true && m.getRectangleMonster().intersects(getRectangleWarrior())) {
					WarriorHP--;
					m.attackcontrol=false;
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

		// 2026-11-09 修复（优先级6）：原动画依赖"按住 J"：
		// keyReleased 会立刻把 attackstatus 改回 stand，轻点时循环马上 break，
		// 只播出音效、看不到帧动画，也到不了第 5 帧（impact=true）造成伤害。
		// 现在改为一次性完整播放 6 帧，不再依赖按键是否按住。
		if(Startgame.jumpstatus.equals("jump")==false) {

			for(int index=0;index<6;index++) {

				if(Warrior.deadcontrol==true) break;

				if(index==1) {
					soundplayer.attack();
				}

				if(Warrior.facing.equals("left")) Warrior.showpicture=getimage.leftattack.get(index);
				else Warrior.showpicture=getimage.rightattack.get(index);

				if(index==5) Warrior.impact=true;
				else Warrior.impact=false;

				try {
					thread.sleep(100);//图片切换速度
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		Warrior.impact=false;

		// 2026-11-09 修复（优先级6）：攻击结束后恢复站立帧，避免角色定格在攻击姿势。
		// 若正在移动，则交给 right/left 线程继续刷新走路帧。
		if(Startgame.status.equals("left")==false&&Startgame.status.equals("right")==false) {
			Warrior.showpicture=Warrior.facing.equals("left")?getimage.warriorstandleft:getimage.warriorstandright;
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
			// 2026-11-09 修复（优先级6）：攻击动画播放期间（attackcontrol==false）不要用走路帧
			// 覆盖攻击帧，否则松手后"边移动边攻击"会被走路动画抢帧导致闪烁/看不到攻击。
			if(Warrior.attackcontrol==true) {
				if(Startgame.jumpstatus.equals("jump")==false&&Startgame.attackstatus.equals("stand")) Warrior.showpicture=getimage.right.get(index);//循环切换图片
				else if(Startgame.attackstatus.equals("stand"))Warrior.showpicture=getimage.warriorstandright;
			}
			
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
		// 2026-11-09 修复（优先级6）：攻击动画进行中时不要覆盖攻击帧。
		if(Warrior.attackcontrol==true) Warrior.showpicture=getimage.warriorstandright;
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
			// 2026-11-09 修复（优先级6）：同上，攻击期间不要覆盖攻击帧。
			if(Warrior.attackcontrol==true) {
				if(Startgame.jumpstatus.equals("jump")==false&&Startgame.attackstatus.equals("stand")) Warrior.showpicture=getimage.left.get(index);
				else if(Startgame.attackstatus.equals("stand")) Warrior.showpicture=getimage.warriorstandleft;
			}
			
			
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
		// 2026-11-09 修复（优先级6）：攻击动画进行中时不要覆盖攻击帧。
		if(Warrior.attackcontrol==true) Warrior.showpicture=getimage.warriorstandleft;
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

		// 2026-11-09 修复（优先级6）：步长至少为 1，避免极小分辨率下 height/160 == 0 造成死循环。
		int step = Math.max(1, Startgame.height/160);

		while(true) {

			if(Warrior.deadcontrol==true) break;

		try {
			thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
			if(Startgame.jumpstatus.equals("jump")&&Warrior.y>=Startgame.height/5) Warrior.y-=step;
			else break;
		}
		while(true) {
			try {
				thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			Warrior.y+=step;
			// 2026-11-09 修复（优先级6）：原用 y==height/2 精确相等判断，一旦步长不对齐会永远落不下去；
			// 改为"到达或越过"即落地，并修正到准确高度。
			if(Warrior.y>=Startgame.height/2) {
				Warrior.y=Startgame.height/2;
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
