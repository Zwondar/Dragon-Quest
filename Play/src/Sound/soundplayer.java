package Sound;

import java.io.File;

public class soundplayer {
	
	static File backgrandzerofile=new File("src/Sound/At beginning.wav");//开始界面背景音效
	static File backgrandonefile=new File("src/Sound/Kingbreaker.wav");//第一关游戏界面界面背景音效
	static File recover=new File("src/Sound/recover.wav");
	static File fireball=new File("src/Sound/fireball(1).wav");
	static File choose=new File("src/Sound/choose.wav");
	static File enter=new File("src/Sound/enter.wav");
	static File jump=new File("src/Sound/jump.wav");
	static File sword=new File("src/Sound/sword_down.wav");
	static File dead=new File("src/Sound/cha_death_01(1).wav");
	static File attack=new File("src/Sound/use_sword_01.wav");
	static File mondead=new File("src/Sound/mo_death.wav");
	static File walk=new File("src/Sound/walk.wav");
 
	private static void play(File soundfile,boolean circuture) {
		
		player p=new player(soundfile, circuture);
		
		p.play();
		
	}
	
private static void playbackgrand(File soundfile,boolean circuture) {
		
		player p=new player(soundfile, circuture);
		
		p.playbackgrand();
		
	}
	
	public static void backgrandzero() {//初始化开始界面背景音乐
		
		playbackgrand(backgrandzerofile,true);
		
	}
	
public static void backgrandone() {//初始化第一关游戏界面背景音乐
		
		playbackgrand(backgrandonefile,true);
		
	}

public static void recover() {
	
	play(recover,false);
	
}

public static void fireball() {
	
	play(fireball,false);
	
}

public static void choose() {
	
	play(choose,false);
}

public static void enter() {
	
	play(enter,false);
}

public static void jump() {
	
	play(jump,false);
}
public static void sword() {
	
	play(sword,false);
}
public static void dead() {
	
	play(dead,false);
}
public static void attack() {
	
	play(attack,false);
}
public static void mondead() {
	
	play(mondead,false);
}
public static void walk() {
	
	play(walk,false);
}
}
