package image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;

import frame.Startgame;
import tool.Monster;
import tool.Monster2;
import tool.Monster3;
import tool.Slime;
import tool.backgrand;
/*
图片素材库
 */
public class getimage {
public static BufferedImage win;
public static BufferedImage gameover;
public static BufferedImage backgrandfirst;
public static BufferedImage pointer;
public static BufferedImage backgrandtwo;
public static BufferedImage backgrandthree;
public static BufferedImage backgrandfour;
public static BufferedImage netWork;
public static BufferedImage warriorstandright;
public static BufferedImage warriorstandleft;

public static BufferedImage warriorrightone;
public static BufferedImage warriorrighttwo;
public static BufferedImage warriorrightthree;
public static BufferedImage warriorrightfour;
public static BufferedImage warriorrightfive;
public static BufferedImage warriorrightsix;
public static BufferedImage warriorrightseven;
public static BufferedImage warriorrightattack1;
public static BufferedImage warriorrightattack2;
public static BufferedImage warriorrightattack3;
public static BufferedImage warriorrightattack4;
public static BufferedImage warriorrightattack5;
public static BufferedImage warriorrightattack6;

public static BufferedImage obstaletree;

public static BufferedImage warriorleftone;
public static BufferedImage warriorlefttwo;
public static BufferedImage warriorleftthree;
public static BufferedImage warriorleftfour;
public static BufferedImage warriorleftfive;
public static BufferedImage warriorleftsix;
public static BufferedImage warriorleftseven;
public static BufferedImage warriorleftattack1;
public static BufferedImage warriorleftattack2;
public static BufferedImage warriorleftattack3;
public static BufferedImage warriorleftattack4;
public static BufferedImage warriorleftattack5;
public static BufferedImage warriorleftattack6;

public static BufferedImage warriorrightdead1;
public static BufferedImage warriorrightdead2;
public static BufferedImage warriorrightdead3;

public static BufferedImage warriorleftdead1;
public static BufferedImage warriorleftdead2;
public static BufferedImage warriorleftdead3;

public static BufferedImage monsterrunright1;
public static BufferedImage monsterrunright2;
public static BufferedImage monsterrunright3;
public static BufferedImage monsterrunright4;
public static BufferedImage monsterrunright5;
public static BufferedImage monsterrunright6;
public static BufferedImage monsterrunright7;

public static BufferedImage monsterrunleft1;
public static BufferedImage monsterrunleft2;
public static BufferedImage monsterrunleft3;
public static BufferedImage monsterrunleft4;
public static BufferedImage monsterrunleft5;
public static BufferedImage monsterrunleft6;
public static BufferedImage monsterrunleft7;

public static BufferedImage monsterdeadright1;
public static BufferedImage monsterdeadright2;

public static BufferedImage monsterdeadleft1;
public static BufferedImage monsterdeadleft2;

public static BufferedImage slimeleft1;
public static BufferedImage slimeleft2;
public static BufferedImage slimeleft3;
public static BufferedImage slimeleft4;
public static BufferedImage slimeleft5;
public static BufferedImage slimeleft6;
public static BufferedImage slimeleft7;
public static BufferedImage slimeleft8;

public static BufferedImage slimeright1;
public static BufferedImage slimeright2;
public static BufferedImage slimeright3;
public static BufferedImage slimeright4;
public static BufferedImage slimeright5;
public static BufferedImage slimeright6;
public static BufferedImage slimeright7;
public static BufferedImage slimeright8;

public static BufferedImage Revitalize1;
public static BufferedImage Revitalize2;
public static BufferedImage Revitalize3;
public static BufferedImage Revitalize4;

public static BufferedImage sword;
public static BufferedImage stone;

public static BufferedImage greenleft1;
public static BufferedImage greenleft2;
public static BufferedImage greenleft3;
public static BufferedImage greenleft4;

public static BufferedImage greendeadleft1;
public static BufferedImage greendeadleft2;
public static BufferedImage greendeadleft3;
public static BufferedImage greendeadleft4;

public static BufferedImage greenright1;
public static BufferedImage greenright2;
public static BufferedImage greenright3;
public static BufferedImage greenright4;

public static BufferedImage greendeadright1;
public static BufferedImage greendeadright2;
public static BufferedImage greendeadright3;
public static BufferedImage greendeadright4;

public static BufferedImage redleft1;
public static BufferedImage redleft2;
public static BufferedImage redleft3;
public static BufferedImage redleft4;
public static BufferedImage redleft5;

public static BufferedImage redright1;
public static BufferedImage redright2;
public static BufferedImage redright3;
public static BufferedImage redright4;
public static BufferedImage redright5;

public static BufferedImage redrightdead1;
public static BufferedImage redrightdead2;
public static BufferedImage redrightdead3;
public static BufferedImage redrightdead4;

public static BufferedImage redleftdead1;
public static BufferedImage redleftdead2;
public static BufferedImage redleftdead3;
public static BufferedImage redleftdead4;

  public static List<BufferedImage> right=new ArrayList<BufferedImage>();
  public static List<BufferedImage> left=new ArrayList<BufferedImage>();
  public static List<BufferedImage> rightattack=new ArrayList<BufferedImage>();
  public static List<BufferedImage> leftattack=new ArrayList<BufferedImage>();
  public static List<BufferedImage> rightdead=new ArrayList<BufferedImage>();
  public static List<BufferedImage> leftdead=new ArrayList<BufferedImage>();
  public static List<BufferedImage> monsterrunright=new ArrayList<BufferedImage>();
  public static List<BufferedImage> monsterrunleft=new ArrayList<BufferedImage>();
  public static List<BufferedImage> monsterdeadright=new ArrayList<BufferedImage>();
  public static List<BufferedImage> monsterdeadleft=new ArrayList<BufferedImage>();
  // 2026-11-09 修复（优先级2）：实体集合会被 time/paint/技能/死亡等多个线程并发增删，
  // 改用 CopyOnWriteArrayList，避免遍历时抛 ConcurrentModificationException / IndexOutOfBoundsException。
  public static List<Monster> monster=new CopyOnWriteArrayList<Monster>();
  public static List<BufferedImage> Revitalize=new ArrayList<BufferedImage>();
  public static List<BufferedImage> slimeleft=new ArrayList<BufferedImage>();
  public static List<BufferedImage> slimeright=new ArrayList<BufferedImage>();
  // 2026-11-09 修复（优先级2）：同上，改为线程安全集合。
  public static List<Slime> slime =new CopyOnWriteArrayList<Slime>();
  public static List<BufferedImage> greenleft=new ArrayList<BufferedImage>();
  public static List<BufferedImage> redleft=new ArrayList<BufferedImage>();
  public static List<BufferedImage> redright=new ArrayList<BufferedImage>();
  public static List<BufferedImage> greenright=new ArrayList<BufferedImage>();
  public static List<BufferedImage> greendeadleft=new ArrayList<BufferedImage>();
  public static List<BufferedImage> reddeadleft=new ArrayList<BufferedImage>();
  public static List<BufferedImage> reddeadright=new ArrayList<BufferedImage>();
  public static List<BufferedImage> greendeadright=new ArrayList<BufferedImage>();
  // 2026-11-09 修复（优先级2）：同上，改为线程安全集合。
  public static List<Monster3> red=new CopyOnWriteArrayList<Monster3>();
  public static List<Monster2> green=new CopyOnWriteArrayList<Monster2>();

/**
 * 2026-11-09 修复（优先级5）：改为从 classpath 读取资源。
 * 原代码使用 new File("src/image/...")，依赖启动时的工作目录，
 * 打包成 jar 或从其他目录启动时会全部加载失败。现在可随 class 文件一起发布。
 */
private static BufferedImage load(String path) {
	try (InputStream in = getimage.class.getResourceAsStream(path)) {
		if (in == null) {
			System.err.println("图片资源不存在: " + path);
			return null;
		}
		return ImageIO.read(in);
	} catch (IOException e) {
		System.err.println("图片资源加载失败: " + path);
		e.printStackTrace();
		return null;
	}
}

public void init() {//初始化图片
		win=load("/image/VICTORY(1).png");
		gameover=load("/image/GAMEOVER.png");
		netWork=load("/image/Login.jpg");
		backgrandfirst=load("/image/backgrand.jpg");
		pointer=load("/image/sword.png");
		backgrandtwo=load("/image/background1.png");
		backgrandthree=load("/image/background2.png");
		backgrandfour=load("/image/background3.png");
		obstaletree=load("/image/R-C.png");
		
		warriorstandright=load("/image/cha_01.png");
		warriorrightone=load("/image/cha_02.png");
		warriorrighttwo=load("/image/cha_03.png");
		warriorrightthree=load("/image/cha_04.png");
		warriorrightfour=load("/image/cha_05.png");
		warriorrightfive=load("/image/cha_06.png");
		warriorrightsix=load("/image/cha_07.png");
		warriorrightseven=load("/image/cha_08.png");
		
		warriorrightattack1=load("/image/cha_17.png");
		warriorrightattack2=load("/image/cha_18.png");
		warriorrightattack3=load("/image/cha_19.png");
		warriorrightattack4=load("/image/cha_20.png");
		warriorrightattack5=load("/image/cha_22.png");
		warriorrightattack6=load("/image/cha_23.png");
		
		warriorrightdead1=load("/image/cha2_01.png");
		warriorrightdead2=load("/image/cha2_02.png");
		warriorrightdead3=load("/image/cha2_03.png");
		
		Revitalize1=load("/image/texiao_01(1).png");
		Revitalize2=load("/image/texiao_02(1).png");
		Revitalize3=load("/image/texiao_03(1).png");
		Revitalize4=load("/image/texiao_04(1).png");
		
		sword=load("/image/sword2(1).png");
		stone=load("/image/stone.png");
		
		right.add(warriorstandright);
		right.add(warriorrightone);
		right.add(warriorrighttwo);
		right.add(warriorrightthree);
		right.add(warriorrightfour);
		right.add(warriorrightfive);
		right.add(warriorrightsix);
		right.add(warriorrightseven);
		
		rightattack.add(warriorrightattack1);
		rightattack.add(warriorrightattack2);
		rightattack.add(warriorrightattack3);
		rightattack.add(warriorrightattack4);
		rightattack.add(warriorrightattack5);
		rightattack.add(warriorrightattack6);
		
		rightdead.add(warriorrightdead1);
		rightdead.add(warriorrightdead2);
		rightdead.add(warriorrightdead3);
		
		warriorstandleft=load("/image/lcha_01.png");
		warriorleftone=load("/image/lcha_02.png");
		warriorlefttwo=load("/image/lcha_03.png");
		warriorleftthree=load("/image/lcha_04.png");
		warriorleftfour=load("/image/lcha_05.png");
		warriorleftfive=load("/image/lcha_06.png");
		warriorleftsix=load("/image/lcha_07.png");
		warriorleftseven=load("/image/lcha_08.png");
		
		warriorleftattack1=load("/image/lcha_17.png");
		warriorleftattack2=load("/image/lcha_18.png");
		warriorleftattack3=load("/image/lcha_19.png");
		warriorleftattack4=load("/image/lcha_20.png");
		warriorleftattack5=load("/image/lcha_22.png");
		warriorleftattack6=load("/image/lcha_23.png");
		
		warriorleftdead1=load("/image/lcha2_01.png");
		warriorleftdead2=load("/image/lcha2_02.png");
		warriorleftdead3=load("/image/lcha2_03.png");
		
		monsterrunright1=load("/image/mos2_01.png");
		monsterrunright2=load("/image/mos2_02.png");
		monsterrunright3=load("/image/mos2_03.png");
		monsterrunright4=load("/image/mos2_04.png");
		monsterrunright5=load("/image/mos2_05.png");
		monsterrunright6=load("/image/mos2_06.png");
		monsterrunright7=load("/image/mos2_07.png");
		
		monsterrunleft1=load("/image/lmos2_01.png");
		monsterrunleft2=load("/image/lmos2_02.png");
		monsterrunleft3=load("/image/lmos2_03.png");
		monsterrunleft4=load("/image/lmos2_04.png");
		monsterrunleft5=load("/image/lmos2_05.png");
		monsterrunleft6=load("/image/lmos2_06.png");
		monsterrunleft7=load("/image/lmos2_07.png");
		
		monsterdeadleft1=load("/image/lmos3_01.png");
		monsterdeadleft2=load("/image/lmos3_02.png");
		
		monsterdeadright1=load("/image/mos3_01.png");
		monsterdeadright2=load("/image/mos3_02.png");
		
		slimeleft1=load("/image/lmos1_01.png");
		slimeleft2=load("/image/lmos1_02.png");
		slimeleft3=load("/image/lmos1_03.png");
		slimeleft4=load("/image/lmos1_04.png");
		slimeleft5=load("/image/lmos1_05.png");
		slimeleft6=load("/image/lmos1_06.png");
		slimeleft7=load("/image/lmos1_07.png");
		slimeleft8=load("/image/lmos1_08.png");
		
		slimeright1=load("/image/lmos1r_01.png");
		slimeright2=load("/image/lmos1r_02.png");
		slimeright3=load("/image/lmos1r_03.png");
		slimeright4=load("/image/lmos1r_04.png");
		slimeright5=load("/image/lmos1r_05.png");
		slimeright6=load("/image/lmos1r_06.png");
		slimeright7=load("/image/lmos1r_07.png");
		slimeright8=load("/image/lmos1r_08.png");
		
		greenleft1=load("/image/lgbrin_01.png");
		greenleft2=load("/image/lgbrin_02.png");
		greenleft3=load("/image/lgbrin_03.png");
		greenleft4=load("/image/lgbrin_04.png");
		
		greenright1=load("/image/rgbrin_01.png");
		greenright2=load("/image/rgbrin_02.png");
		greenright3=load("/image/rgbrin_03.png");
		greenright4=load("/image/rgbrin_04.png");
		
		greendeadright1=load("/image/rgbirn.d_01.png");
		greendeadright2=load("/image/rgbirn.d_02.png");
		greendeadright3=load("/image/rgbirn.d_03.png");
		greendeadright4=load("/image/rgbirn.d_04.png");
		
		greendeadleft1=load("/image/lgbirn.d_01.png");
		greendeadleft2=load("/image/lgbirn.d_02.png");
		greendeadleft3=load("/image/lgbirn.d_03.png");
		greendeadleft4=load("/image/lgbirn.d_04.png");
		
		redleft1=load("/image/ledgbrin_01.png");
		redleft2=load("/image/ledgbrin_02.png");
		redleft3=load("/image/ledgbrin_03.png");
		redleft4=load("/image/ledgbrin_04.png");
		redleft5=load("/image/ledgbrin_05.png");
		
		redright1=load("/image/redgbrin_01.png");
		redright2=load("/image/redgbrin_02.png");
		redright3=load("/image/redgbrin_03.png");
		redright4=load("/image/redgbrin_04.png");
		redright5=load("/image/redgbrin_05.png");
		
		redrightdead1=load("/image/redgbrin.d_01_01.png");
		redrightdead2=load("/image/redgbrin.d_01_02.png");
		redrightdead3=load("/image/redgbrin.d_01_03.png");
		redrightdead4=load("/image/redgbrin.d_01_04.png");
		
		redleftdead1=load("/image/ledgbrin.d_01_01.png");
		redleftdead2=load("/image/ledgbrin.d_01_02.png");
		redleftdead3=load("/image/ledgbrin.d_01_03.png");
		redleftdead4=load("/image/ledgbrin.d_01_04.png");

		greenleft.add(greenleft1);
		greenleft.add(greenleft2);
		greenleft.add(greenleft3);
		greenleft.add(greenleft4);
		
		greenright.add(greenright1);
		greenright.add(greenright2);
		greenright.add(greenright3);
		greenright.add(greenright4);
		
		greendeadleft.add(greendeadleft1);
		greendeadleft.add(greendeadleft2);
		greendeadleft.add(greendeadleft3);
		greendeadleft.add(greendeadleft4);
		
		greendeadright.add(greendeadright1);
		greendeadright.add(greendeadright2);
		greendeadright.add(greendeadright3);
		greendeadright.add(greendeadright4);
		
		redleft.add(redleft1);
		redleft.add(redleft2);
		redleft.add(redleft3);
		redleft.add(redleft4);
		redleft.add(redleft5);
		
		redright.add(redright1);
		redright.add(redright2);
		redright.add(redright3);
		redright.add(redright4);
		redright.add(redright5);
		
		reddeadleft.add(redleftdead1);
		reddeadleft.add(redleftdead2);
		reddeadleft.add(redleftdead3);
		reddeadleft.add(redleftdead4);
		
		reddeadright.add(redrightdead1);
		reddeadright.add(redrightdead2);
		reddeadright.add(redrightdead3);
		reddeadright.add(redrightdead4);
		
		
		
		left.add(warriorstandleft);
		left.add(warriorleftone);
		left.add(warriorlefttwo);
		left.add(warriorleftthree);
		left.add(warriorleftfour);
		left.add(warriorleftfive);
		left.add(warriorleftsix);
		left.add(warriorleftseven);
		
		leftattack.add(warriorleftattack1);
		leftattack.add(warriorleftattack2);
		leftattack.add(warriorleftattack3);
		leftattack.add(warriorleftattack4);
		leftattack.add(warriorleftattack5);
		leftattack.add(warriorleftattack6);
		
		leftdead.add(warriorleftdead1);
		leftdead.add(warriorleftdead2);
		leftdead.add(warriorleftdead3);
		
		monsterrunright.add(monsterrunright1);
		monsterrunright.add(monsterrunright2);
		monsterrunright.add(monsterrunright3);
		monsterrunright.add(monsterrunright4);
		monsterrunright.add(monsterrunright5);
		monsterrunright.add(monsterrunright6);
		monsterrunright.add(monsterrunright7);
		
		monsterdeadright.add(monsterdeadright1);
		monsterdeadright.add(monsterdeadright2);
		
		monsterrunleft.add(monsterrunleft1);
		monsterrunleft.add(monsterrunleft2);
		monsterrunleft.add(monsterrunleft3);
		monsterrunleft.add(monsterrunleft4);
		monsterrunleft.add(monsterrunleft5);
		monsterrunleft.add(monsterrunleft6);
		monsterrunleft.add(monsterrunleft7);
		
		monsterdeadleft.add(monsterdeadleft1);
		monsterdeadleft.add(monsterdeadleft2);
		
		slimeleft.add(slimeleft1);
		slimeleft.add(slimeleft2);
		slimeleft.add(slimeleft3);
		slimeleft.add(slimeleft4);
		slimeleft.add(slimeleft5);
		slimeleft.add(slimeleft6);
		slimeleft.add(slimeleft7);
		slimeleft.add(slimeleft8);
		
		slimeright.add(slimeright1);
		slimeright.add(slimeright2);
		slimeright.add(slimeright3);
		slimeright.add(slimeright4);
		slimeright.add(slimeright5);
		slimeright.add(slimeright6);
		slimeright.add(slimeright7);
		slimeright.add(slimeright8);
		
		Revitalize.add(Revitalize1);
		Revitalize.add(Revitalize2);
		Revitalize.add(Revitalize3);
		Revitalize.add(Revitalize4);
}

}
