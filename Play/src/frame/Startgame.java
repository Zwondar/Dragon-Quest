package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

import Sound.soundplayer;
import image.getimage;
import netWork.client;
import netWork.netframe;
import tool.Monster;
import tool.Monster2;
import tool.Monster3;
import tool.MyKeyListner;
import tool.Obstale;
import tool.Skill;
import tool.Slime;
import tool.Warrior;
import tool.backgrand;
import tool.over;
import tool.time;

public class Startgame extends JFrame{

// 2026-11-09 修复（优先级1）：固定约 60FPS 的游戏循环，避免空转占满 CPU。
private static final long FRAME_TIME_NANOS = 1000000000L / 60;

// 2026-11-09 修复（优先级1）：上一帧绘制时间，用于计算与帧率无关的时间增量。
private long lastPaintNanos = 0;

// 2026-11-09 修复（优先级1）：供背景/场景移动使用的帧时间增量（秒），默认按 1/60 秒计算。
public static double delta = 1.0 / 60;

// 2026-11-09 修复（优先级1）：整屏离屏缓冲只创建一次并复用，不再每帧 createImage。
private Image frameImage;

public  static Image image;//主图片（保留字段以兼容旧代码，实际绘制使用 frameImage）

public static int width;//屏幕的宽度

public static int height;//屏幕的高度

public static int checkpoint=0;//当前关卡

public static long StartTime;//游戏开始的时间

public static String status="stand";//游戏人物的状态

public static String jumpstatus="stand";//游戏人物的状态

public static String attackstatus="stand";//游戏人物的攻击状态

public static boolean netcontrol=false;

public static boolean datacontrol=false;

// 2026-11-09 新增（优先级6）：记录当前难度（1=简单/2=中等/3=困难），
// 供游戏结束/重新开始时按难度重置血量，原代码固定重置为 45 点，忽略难度选择。
public static int difficulty=1;

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
	
	// 2026-11-09 修复（优先级1）：原 while(true){repaint();} 无任何休眠，会把一个 CPU 核心跑满。
	// 改为固定帧率循环：每帧重绘后主动休眠到下一帧。
	while(true) {
		long frameStart = System.nanoTime();

		repaint();

		if(netcontrol==true) {

			netcontrol=false;

			// 2026-11-09 修复（优先级2/4）：原代码在主线程用模态对话框打开公屏聊天，
			// 会阻塞整个渲染循环导致游戏卡死。这里改为非模态窗口并在 EDT 上创建。
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// 2026-11-09 修复（优先级2）：复用同一个聊天窗口，避免重复打开产生多个窗口。
					netframe.showChat(Startgame.this);
				}
			});
		}

		long sleepNanos = FRAME_TIME_NANOS - (System.nanoTime() - frameStart);
		if(sleepNanos > 0) {
			try {
				Thread.sleep(sleepNanos / 1000000L, (int)(sleepNanos % 1000000L));
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
	public void paint(Graphics g) {//重构paint方法

	// 2026-11-09 修复（优先级1）：计算帧间时间增量，使移动速度不再依赖帧率/机器性能。
	long now = System.nanoTime();
	if(lastPaintNanos != 0) {
		delta = (now - lastPaintNanos) / 1000000000.0;
		if(delta <= 0) delta = 1.0 / 60;
		if(delta > 0.1) delta = 0.1;// 卡顿时限制单帧位移，避免"瞬移"
	}
	lastPaintNanos = now;

	// 2026-11-09 修复（优先级1）：原每帧 createImage 重建整屏缓冲，产生大量临时对象。
	// 改为只创建一次并复用同一张离屏图像。
	if(frameImage == null) {
		frameImage = createImage(width, height);
	}

	Graphics ig = frameImage.getGraphics();//获得复用缓冲的画笔

	// 2026-11-09 修复（优先级1）：复用缓冲前先清屏，避免上一帧残影。
	ig.setColor(Color.BLACK);
	ig.fillRect(0, 0, width, height);

		b.paintself(ig);
		if(checkpoint == 1) {

			if(Obstale.treeHP>0) {
				obstale.paintself(ig);
			}

				warrior.paintself(ig);

				// 2026-11-09 修复（优先级3）：改为增强 for（CopyOnWriteArrayList 迭代安全）；
				// 史莱姆没有死亡动画，血量<=0 时立即移除，避免列表无限增长、死后仍能伤害玩家。
				for (Slime s : getimage.slime) {
					if (s.SlimeHp <= 0) {
						getimage.slime.remove(s);
						continue;
					}
					if (backgrand.showpicture == getimage.backgrandthree) s.paintself(ig);
				}

				// 2026-11-09 修复（优先级3）：死亡动画结束（over==false）且血量<=0 后移除怪物，防止内存泄漏。
				for (Monster m : getimage.monster) {
					if (m.MinsterHp <= 0 && !m.over) {
						getimage.monster.remove(m);
						continue;
					}
					if (m.over) m.paintself(ig);
				}

				for (Monster3 m : getimage.red) {
					if (m.MinsterHp <= 0 && !m.over) {
						getimage.red.remove(m);
						continue;
					}
					if (backgrand.showpicture == getimage.backgrandtwo && m.over) m.paintself(ig);
				}

				for (Monster2 m : getimage.green) {
					if (m.MinsterHp <= 0 && !m.over) {
						getimage.green.remove(m);
						continue;
					}
					if (backgrand.showpicture == getimage.backgrandfour && m.over) m.paintself(ig);
				}

		}

		if(Skill.skillcontrol==true) skill.paintself(ig);

		if(over.control==true) o.paintself(ig);

			g.drawImage(frameImage, 0, 0, width, height, this);//对窗体进行绘制

		ig.dispose();

	}

}
