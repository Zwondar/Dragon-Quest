package Sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import frame.Startgame;

public class player implements Runnable{//音乐播放器
	
	public boolean soundcontrol=true;//控制背景音乐的播放
	
File soundFile;//音乐文件

public static Thread threadbackgrand;

public  Thread thread;//用于启动线程

boolean circulate;//控制是否循环

public player() {
	
}

public player(File soundFile,boolean circulate) {
	
	this.circulate=circulate;
	
	this.soundFile=soundFile;
	
    }

public void run() {
	
	byte bufferarea[]=new byte[1024*128];//创建128K缓冲区
	
	do {
		
		AudioInputStream audioinputstream=null;//创建音频输入流
		
		SourceDataLine sourcedataline=null;//创建混音频数据行
		
		try {
			
			audioinputstream=AudioSystem.getAudioInputStream(soundFile);//获得音频数据
			
			AudioFormat fomart=audioinputstream.getFormat();//获得当前音频的音乐格式
			
			DataLine.Info info=new DataLine.Info(SourceDataLine.class, fomart);//创建数据行
			
			sourcedataline=(SourceDataLine)AudioSystem.getLine(info);//获得当前音频行对象
			
			sourcedataline.open(fomart);//按照当前音频格式打开源数据行
			
			sourcedataline.start();//开始进行读写操作
			
			int count=0;//记录当前流读出的字节数
			
			while(count!=-1) {//如果count不等于-1，说明流未到结尾处
				
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
			sourcedataline.drain();
			sourcedataline.close();
		}
		
		
	}while(circulate);
	
     }

public void play() {//启动线程
	
	thread=new Thread(this);
	
	thread.start();
	
}
public void playbackgrand() {//启动线程
	
	threadbackgrand=new Thread(this);
	
	threadbackgrand.start();
	
}

}
