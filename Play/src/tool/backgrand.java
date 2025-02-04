package tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Thread.State;

import javax.swing.JButton;
import javax.swing.JTextField;

import Sound.player;
import Sound.soundplayer;
import frame.Startgame;
import image.getimage;
import netWork.client;
import netWork.netframe;

public class backgrand{
	
	public static int fonty=Startgame.height/4+Startgame.height/6;
	/*
	 * 游戏开始界面指针y
	 * 
	 * */
	public static int y=fonty-Startgame.height/17;//初始化sword坐标
	/*
	 * 背景移动速度（与人物速度一致）
	 * */
	 int speed=Warrior.speed/5;
	/*
	 * 关卡背景图片的坐标x
	 * */
	public static int x;
	
	public static BufferedImage showpicture=getimage.backgrandtwo;
	
	public static boolean backgrandmoveright=false;//控制背景移动
	
	public static boolean backgrandmoveleft=false;//控制背景移动
	
	public static boolean soundcontrol=true;//控制背景音乐的播放
	
public void paintself(Graphics g) {
	
	music();
	
	if(Startgame.checkpoint==0) {
		
	g.drawImage(getimage.backgrandfirst,0,0, Startgame.width, Startgame.height, null);
	
	g.drawImage(getimage.pointer,Startgame.width/6,y,Startgame.width/4,Startgame.height/15, null);
	
	g.setColor(Color.BLACK);
	
	g.setFont(new Font("黑体", Font.BOLD, 60));
	
	g.drawString("开始游戏", Startgame.width/2-100,fonty);
	
	g.drawString("选择难度",Startgame.width/2-100, fonty+Startgame.height/10);
	
	g.drawString("切换关卡",Startgame.width/2-100, fonty+Startgame.height/10*2);
	
	g.drawString("公屏聊天",Startgame.width/2-100, fonty+Startgame.height/10*3);
	
	g.drawString("结束游戏", Startgame.width/2-100,fonty+Startgame.height/10*4);
	
	
	}else if(Startgame.checkpoint==1) {
		move();
		g.drawImage(showpicture,x,0, Startgame.width*2, Startgame.height, null);
		
	}else if(Startgame.checkpoint==-1) {
		
		g.drawImage(getimage.backgrandfirst,0,0, Startgame.width, Startgame.height, null);
		
		g.drawImage(getimage.backgrandtwo,Startgame.width/3,0,Startgame.width/3*2,Startgame.height/3, null);
		
		g.drawImage(getimage.backgrandthree,Startgame.width/3,Startgame.height/3,Startgame.width/3*2,Startgame.height/3, null);
		
		g.drawImage(getimage.backgrandfour,Startgame.width/3,Startgame.height/3*2,Startgame.width/3*2,Startgame.height/3, null);
		
		g.drawImage(getimage.pointer,0,y,Startgame.width/4,Startgame.height/15, null);
		
	}else if(Startgame.checkpoint==-2) {
		
		g.setColor(Color.BLACK);
		
		g.setFont(new Font("黑体", Font.BOLD, 60));
		
		g.drawImage(getimage.backgrandfirst,0,0, Startgame.width, Startgame.height, null);
		
		g.drawString("简单难度", Startgame.width/2-100,fonty);
		
		g.drawString("中等难度", Startgame.width/2-100,fonty+Startgame.height/10);
		
		g.drawString("困难难度", Startgame.width/2-100,fonty+Startgame.height/10*2);
		
		g.drawImage(getimage.pointer,Startgame.width/6,y,Startgame.width/4,Startgame.height/15, null);
		
	}
	
}

public void music() {//播放背景音乐
	
	
	if(Startgame.checkpoint==1&&soundcontrol==true) {
		
		player.threadbackgrand.stop();
		
		soundplayer.backgrandone();
		
		soundcontrol=false;
		
	}else if(Startgame.checkpoint==0&&soundcontrol==false) {
		
		player.threadbackgrand.stop();
		
		soundplayer.backgrandzero();
		
		soundcontrol=true;
	
	}
	
}
public void move() {
	if(backgrandmoveright==true&&x>=-Startgame.width) {
		x-=speed;
	    for(int i=0;i<getimage.monster.size();i++) {
	    	getimage.monster.get(i).x-=speed;
	    }
	    for(int i=0;i<getimage.red.size();i++) {
	    	getimage.red.get(i).x-=speed;
	    }
	    for(int i=0;i<getimage.green.size();i++) {
	    	getimage.green.get(i).x-=speed;
	    }
	    for(int i=0;i<getimage.slime.size();i++) {
	    	getimage.slime.get(i).x-=speed;
	    }
		Obstale.x2-=speed;
		Skill.x-=speed;
	}else if(backgrandmoveleft==true&&x<=0) {
		x+=speed;
		 for(int i=0;i<getimage.monster.size();i++) {
		    	getimage.monster.get(i).x+=speed;
		    }
		 for(int i=0;i<getimage.red.size();i++) {
		    	getimage.red.get(i).x+=speed;
		    }
		 for(int i=0;i<getimage.green.size();i++) {
		    	getimage.green.get(i).x+=speed;
		    }
		  for(int i=0;i<getimage.slime.size();i++) {
		    	getimage.slime.get(i).x+=speed;
		    }
		Obstale.x2+=speed;
		Skill.x+=speed;
	}
}

}

