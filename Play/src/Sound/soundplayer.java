package Sound;

import java.net.URL;

public class soundplayer {

	// 2026-11-09 修复（优先级5）：音频改为从 classpath 读取，
	// 不再依赖启动目录下的 "src/Sound/..."。
	static URL backgrandzerofile=resource("At beginning.wav");//开始界面背景音效
	static URL backgrandonefile=resource("Kingbreaker.wav");//第一关游戏界面界面背景音效
	static URL recover=resource("recover.wav");
	static URL fireball=resource("fireball(1).wav");
	static URL choose=resource("choose.wav");
	static URL enter=resource("enter.wav");
	static URL jump=resource("jump.wav");
	static URL sword=resource("sword_down.wav");
	static URL dead=resource("cha_death_01(1).wav");
	static URL attack=resource("use_sword_01.wav");
	static URL mondead=resource("mo_death.wav");
	static URL walk=resource("walk.wav");

	// 2026-11-09 修复（优先级4）：记录当前背景音乐，便于安全停止。
	private static volatile player currentBackground;

	private static URL resource(String name) {
		URL url=soundplayer.class.getResource("/Sound/"+name);
		if(url==null) {
			System.err.println("音频资源不存在: /Sound/"+name);
		}
		return url;
	}

	private static void play(URL soundfile,boolean circuture) {

		if(soundfile==null) return;

		player p=new player(soundfile, circuture);

		p.play();

	}

private static void playbackgrand(URL soundfile,boolean circuture) {

		// 2026-11-09 修复（优先级4）：切换背景音乐前先停止上一首，避免叠加播放。
		stopBackgrand();

		if(soundfile==null) return;

		player p=new player(soundfile, circuture);

		currentBackground=p;

		p.playbackgrand();

	}

// 2026-11-09 修复（优先级4）：暴露给 backgrand，用于替换 Thread.stop()。
public static void stopBackgrand() {

	player p=currentBackground;

	currentBackground=null;

	if(p!=null) p.stop();

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
