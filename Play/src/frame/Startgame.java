package frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.List;

import javax.management.monitor.Monitor;
import javax.swing.*;

import Sound.player;
import Sound.soundplayer;
import image.getimage;
import netWork.client;
import netWork.netframe;
import tool.Monster;
import tool.MyKeyListner;
import tool.Obstale;
import tool.Skill;
import tool.Warrior;
import tool.backgrand;
import tool.over;
import tool.time;
import tool.Obstale;

public class Startgame extends JFrame{
	
public  static Image image;//主图片

public static int width;//屏幕的宽度

public static int height;//屏幕的高度

public static int checkpoint=0;//当前关卡

public static long StartTime;//游戏开始的时间

public static String status="stand";//游戏人物的状态

public static String jumpstatus="stand";//游戏人物的状态

public static String attackstatus="stand";//游戏人物的攻击状态

public static boolean netcontrol=false;

public static boolean datacontrol=false;

backgrand b;

getimage gi;

Warrior warrior;

Obstale obstale;

 Skill skill;
 
 over o;
 
 public static client c;

public Startgame() {
	
Toolkit tool=Toolkit.getDefaultToolkit();//获得系统工具

Dimension d=tool.getScreenSize();//利用系统工具获得屏幕尺寸

StartTime=System.currentTimeMillis();//初始化游戏开始时间

width=d.width;

height=d.height;

setBounds(0, 0,width, height);

gi=new getimage();

gi.init();//实例化缓存图片

b=new backgrand();

warrior=new Warrior();

obstale =new Obstale();

 skill=new Skill();
 
 c=new client();
 
 o=new over();
 
 c.play();
 
this.addKeyListener(new MyKeyListner());

setResizable(false);

	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
	soundplayer.backgrandzero();//播放开始界面背景音乐
	
	setVisible(true);
	
	time t=new time();
	
	t.play();
	
	while(true) {		
		
	repaint();
	
	if(netcontrol==true) {
		
		new netframe(this,true);
		
		netcontrol=false;
		
	}
		
}
	
}
	public void paint(Graphics g) {//重构paint方法
	
	image=createImage(width,height);//构建图片大小
	
		Graphics ig=image.getGraphics();//获得image的画笔
		
		b.paintself(ig);
		if(checkpoint == 1) {
			
			
			
			if(Obstale.treeHP>0) {
				obstale.paintself(ig);
			}
				
				warrior.paintself(ig);
				
				for(int i=0;i<getimage.slime.size();i++) {
					
					if(backgrand.showpicture==getimage.backgrandthree&&getimage.slime.get(i).SlimeHp>0) getimage.slime.get(i).paintself(ig);
				}
				
			for(int i=0;i<getimage.monster.size();i++) {
				
				if(getimage.monster.get(i).over==true) getimage.monster.get(i).paintself(ig);
			}
			
for(int i=0;i<getimage.red.size();i++) {
				
				if(backgrand.showpicture==getimage.backgrandtwo&&getimage.red.get(i).over==true) getimage.red.get(i).paintself(ig);
			}

for(int i=0;i<getimage.green.size();i++) {
	
	if(backgrand.showpicture==getimage.backgrandfour&&getimage.green.get(i).over==true) getimage.green.get(i).paintself(ig);
}
			
		}
		
		if(Skill.skillcontrol==true) skill.paintself(ig);
		
		if(over.control==true) o.paintself(ig);
		
			g.drawImage(image, 0, 0, width, height, this);//对窗体进行绘制
	
	}

}
