package tool;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Sound.soundplayer;
import frame.Startgame;
import image.getimage;

public class Skill implements Runnable{
	
	public static BufferedImage skillshowpicture;
	
	public static boolean skillcontrol=false;
	
	public static String Skillstatus=" ";
	
	public static int x;
	
	public static int y;
	
	public static int width;
	
	public static int height;
	
	int speed=2;
	
	static Thread thread;
	

	public void paintself(Graphics g) {
		
		g.drawImage(skillshowpicture,x,y,width,height, null);
		
	}

	
	public void run() {
		
		skillcontrol=true;
		
	
			if(Skill.Skillstatus.equals("Revitalize")) {
				
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
			}else if(Skill.Skillstatus.equals("Sword")) {
				
				soundplayer.sword();
				
				skillshowpicture=getimage.sword;
				
				x=Warrior.x+400;
				
				y=-Startgame.height/2;
				
				width=Startgame.width/5;
				
				height=Startgame.height/2;
				
				while(true) {
					
					if(y>Startgame.height/5) break;
					
			
					
					y+=speed;
					
					for(int i=0;i<getimage.monster.size();i++) {
						
					if(getskillRectangle().intersects(getimage.monster.get(i).getRectangleMonster())) {
						getimage.monster.get(i).MinsterHp--;
					 }
					
					}
					
					try {
						thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					
				}
				
			}else if(Skill.Skillstatus.equals("stone")) {
				
                  x =Warrior.x+400;
				
				y=-Startgame.height/4;
				
				width=Startgame.width/3;
				
				height=Startgame.height/2;
				
				skillshowpicture=getimage.stone;
				
				soundplayer.fireball();
				
				while(true) {
					
					if(y>Startgame.height/3) break;
					
					
					
					y+=speed;
					for(int i=0;i<getimage.slime.size();i++) {
					if(getskillRectangle().intersects(getimage.slime.get(i).getRectangleMonster())) {
						getimage.slime.get(i).SlimeHp--;
					 }
					}
					for(int i=0;i<getimage.red.size();i++) {
						if(getskillRectangle().intersects(getimage.red.get(i).getRectangleMonster())) {
							getimage.red.get(i).MinsterHp--;
						 }
						}
					for(int i=0;i<getimage.green.size();i++) {
						if(getskillRectangle().intersects(getimage.green.get(i).getRectangleMonster())) {
							getimage.green.get(i).MinsterHp--;
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
			
			
			
			skillcontrol=false;
			
	}
		
		public void play() {
			thread=new Thread(this);
			thread.start();
		}
		
		public Rectangle getskillRectangle() {
			return new Rectangle(x,y,width,height);
		}
		
		
	}
	




