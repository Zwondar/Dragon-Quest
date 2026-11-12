package Sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class player implements Runnable{//音乐播放器

URL soundFile;//音乐文件（2026-11-09：改为 classpath URL，避免依赖工作目录）

public static Thread threadbackgrand;

public  Thread thread;//用于启动线程

boolean circulate;//控制是否循环

// 2026-11-09 修复（优先级4）：替代 Thread.stop() 的协作式停止标志。
private volatile boolean stopRequested=false;

// 2026-11-09 修复（优先级4）：把音频数据行保存为字段，便于 stop() 中安全关闭，也避免 finally 中的 NPE。
private volatile SourceDataLine sourcedataline;

public player(URL soundFile,boolean circulate) {

	this.circulate=circulate;

	this.soundFile=soundFile;

    }

public void run() {

	byte bufferarea[]=new byte[1024*128];//创建128K缓冲区

	do {

		if(stopRequested) break;

		AudioInputStream audioinputstream=null;//创建音频输入流

		sourcedataline=null;//创建混音频数据行

		try {

			audioinputstream=AudioSystem.getAudioInputStream(soundFile);//获得音频数据

			AudioFormat fomart=audioinputstream.getFormat();//获得当前音频的音乐格式

			DataLine.Info info=new DataLine.Info(SourceDataLine.class, fomart);//创建数据行

			sourcedataline=(SourceDataLine)AudioSystem.getLine(info);//获得当前音频行对象

			sourcedataline.open(fomart);//按照当前音频格式打开源数据行

			sourcedataline.start();//开始进行读写操作

			int count=0;//记录当前流读出的字节数

			while(count!=-1&&!stopRequested) {//如果count不等于-1，说明流未到结尾处

				count=audioinputstream.read(bufferarea,0,bufferarea.length);

				if(count>=0) {
					sourcedataline.write(bufferarea, 0, count);
				}

			}


		} catch (UnsupportedAudioFileException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			// 2026-11-09 修复（优先级4）：原 finally 直接 sourcedataline.drain()，
			// 当 getLine/open 失败时 sourcedataline 为 null，会抛 NPE 掩盖真实异常。
			SourceDataLine line=sourcedataline;
			if(line!=null) {
				try { line.drain(); } catch (Exception ignored) {}
				try { line.close(); } catch (Exception ignored) {}
			}
			if(audioinputstream!=null) {
				try { audioinputstream.close(); } catch (IOException ignored) {}
			}
			sourcedataline=null;
		}


	}while(circulate&&!stopRequested);

     }

public void play() {//启动线程

	thread=new Thread(this);

	thread.start();

}
public void playbackgrand() {//启动线程

	threadbackgrand=new Thread(this);

	threadbackgrand.start();

}

// 2026-11-09 修复（优先级4）：用可协作停止替代已废弃且不安全的 Thread.stop()。
public void stop() {

	stopRequested=true;

	SourceDataLine line=sourcedataline;

	if(line!=null) {
		try { line.stop(); } catch (Exception ignored) {}
		try { line.close(); } catch (Exception ignored) {}
	}

}
}
