package tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Thread.State;

import javax.swing.JButton;
import javax.swing.JTextField;

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

		// 2026-11-09 修复（优先级4）：用 soundplayer 的协作式停止替换已废弃的 Thread.stop()。
		soundplayer.stopBackgrand();

		soundplayer.backgrandone();

		soundcontrol=false;

	}else if(Startgame.checkpoint==0&&soundcontrol==false) {

		// 2026-11-09 修复（优先级4）：同上。
		soundplayer.stopBackgrand();

		soundplayer.backgrandzero();

		soundcontrol=true;

	}

}
public void move() {
	// 2026-11-09 修复（优先级1）：原按"每帧固定像素"移动，滚屏速度取决于机器绘制帧率。
	// 现在按 Startgame.delta 换算成与帧率无关的位移，仍以 60FPS 为基准保持原手感。
	int step=Math.max(1, (int)Math.round(speed*Startgame.delta*60));
	if(backgrandmoveright==true&&x>=-Startgame.width) {
		x-=step;
	    // 2026-11-09 修复（优先级2）：改用增强 for，兼容 CopyOnWriteArrayList 并发遍历。
	    for(Monster m:getimage.monster) {
	    	m.x-=step;
	    }
	    for(Monster3 m:getimage.red) {
	    	m.x-=step;
	    }
	    for(Monster2 m:getimage.green) {
	    	m.x-=step;
	    }
	    for(Slime s:getimage.slime) {
	    	s.x-=step;
	    }
		Obstale.x2-=step;
		Skill.x-=step;
	}else if(backgrandmoveleft==true&&x<=0) {
		x+=step;
		 for(Monster m:getimage.monster) {
		    	m.x+=step;
		    }
		 for(Monster3 m:getimage.red) {
		    	m.x+=step;
		    }
		 for(Monster2 m:getimage.green) {
		    	m.x+=step;
		    }
		  for(Slime s:getimage.slime) {
		    	s.x+=step;
		    }
		Obstale.x2+=step;
		Skill.x+=step;
	}
}

}
