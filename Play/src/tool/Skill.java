package tool;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Sound.soundplayer;
import frame.Startgame;
import image.getimage;

public class Skill implements Runnable{

	public static BufferedImage skillshowpicture;

	public static volatile boolean skillcontrol=false;

	public static String Skillstatus=" ";

	public static int x;

	public static int y;

	public static int width;

	public static int height;

	int speed=2;

	// 2026-11-09 修复（优先级6）：每次施法的状态在线程启动时快照，
	// 不再让运行中的线程去读会被后续按键改写的静态 Skillstatus，避免"技能串台"。
	private volatile String castStatus="";

	private Thread thread;


	public void paintself(Graphics g) {

		// 2026-11-09 修复（优先级2）：图片尚未初始化时不要绘制，避免 NPE。
		if(skillshowpicture==null) return;

		g.drawImage(skillshowpicture,x,y,width,height, null);

	}


	public void run() {

		try {

			if("Revitalize".equals(castStatus)) {

				width=Startgame.width/12;

				height=Startgame.height/3;

				x=Warrior.x;

				y=Warrior.y;

				soundplayer.recover();

			for(int i=0;i<4;i++) {

				skillshowpicture=getimage.Revitalize.get(i);

	            x=Warrior.x;

				y=Warrior.y;

				try {
					thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

				if(i==3) {
					Warrior.WarriorHP+=10;
					if(Warrior.WarriorHP>50) Warrior.WarriorHP=50;
				}

			}
			}else if("Sword".equals(castStatus)) {

				soundplayer.sword();

				skillshowpicture=getimage.sword;

				x=Warrior.x+400;

				y=-Startgame.height/2;

				width=Startgame.width/5;

				height=Startgame.height/2;

				while(true) {

					if(y>Startgame.height/5) break;

					// 2026-11-09 修复（优先级6）：玩家死亡时立即中断施法，避免继续修改实体状态。
					if(Warrior.deadcontrol==true) break;

					y+=speed;

					for(Monster m:getimage.monster) {

					if(m.MinsterHp>0&&getskillRectangle().intersects(m.getRectangleMonster())) {
						m.MinsterHp--;
					 }

					}

					try {
						thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}

				}

			}else if("stone".equals(castStatus)) {

                x =Warrior.x+400;

				y=-Startgame.height/4;

				width=Startgame.width/3;

				height=Startgame.height/2;

				skillshowpicture=getimage.stone;

				soundplayer.fireball();

				while(true) {

					if(y>Startgame.height/3) break;

					if(Warrior.deadcontrol==true) break;

					y+=speed;
					// 2026-11-09 修复（优先级3）：只对存活目标造成伤害。
					for(Slime s:getimage.slime) {
					if(s.SlimeHp>0&&getskillRectangle().intersects(s.getRectangleMonster())) {
						s.SlimeHp--;
					 }
					}
					for(Monster3 m:getimage.red) {
						if(m.MinsterHp>0&&getskillRectangle().intersects(m.getRectangleMonster())) {
							m.MinsterHp--;
						 }
						}
					for(Monster2 m:getimage.green) {
						if(m.MinsterHp>0&&getskillRectangle().intersects(m.getRectangleMonster())) {
							m.MinsterHp--;
						 }
						}

					try {
						thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}


			}

		}finally {
			// 2026-11-09 修复（优先级6）：无论正常结束还是异常，都必须释放施法锁，
			// 否则一次异常会导致之后再也放不出技能。
			skillcontrol=false;
		}

	}

		// 2026-11-09 修复（优先级6）：施法前加锁，防止按住技能键时按键重复
		// 不断创建线程、重复扣蓝、多个技能同时播放。返回是否成功开始施法。
		public synchronized boolean play() {

			if(skillcontrol) return false;

			skillcontrol=true;

			castStatus=Skillstatus;

			thread=new Thread(this);

			thread.start();

			return true;

		}

		public Rectangle getskillRectangle() {
			return new Rectangle(x,y,width,height);
		}


	}
