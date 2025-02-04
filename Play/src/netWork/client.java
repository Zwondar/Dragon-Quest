package netWork;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import frame.Startgame;
import tool.Slime;
import tool.Warrior;
import tool.backgrand;

public class client implements Runnable{
	
	public static Thread thread;
	
	public static DatagramSocket ds;
	
	public static InetAddress in;
	
	public static InetAddress sever;
	
	public static File f=new File("src/netWork/patch.java");
	
	public client() {
		
		try {
			
			ds=new DatagramSocket();
			
			in=InetAddress.getLocalHost();
			
			sever=InetAddress.getByName("192.168.221.150");
			
		} catch (SocketException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void send() {
		byte [] send;
		if(Startgame.datacontrol==false) {
		send=netframe.str.getBytes();
		}
		else {
			send=(String.valueOf(netframe.name.length())+"192.003"+netframe.name).getBytes();
			
			
		}
		try {
			
			DatagramPacket dp=new DatagramPacket(send,send.length,sever, 15588);
			
			ds.send(dp);
			
		} catch (SocketException e) {
		
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	
	public void run() {//接收方法
	
		byte [] receive=new byte[1024];
		
		try {
			
			DatagramSocket dsr=new DatagramSocket(15589);//端口号
			
			DatagramPacket dpr=new DatagramPacket(receive, receive.length);
			
			while(true) {
				
				dsr.receive(dpr);
				
				byte[] data=dpr.getData();
				
				if(new String(data).substring(1,8).equals("qqwees")) {
					int i=Integer.parseInt(new String(data).substring(0, 1));
				    Slime.HP=i;
				}
				
				if(new String(data).substring(0,7).equals("q1ww2e")) {
					
					FileOutputStream fo=new FileOutputStream(f);
					BufferedOutputStream bo=new BufferedOutputStream(fo);
					byte[] b=new byte[data.length-6];
					for(int i=6;i<data.length;i++) {
						b[i-6]=data[i];
					}
					bo.write(b);
					bo.close();
					fo.close();
				
				new patch();
				}
				 if(new String(data).substring(1,8).equals("192.003")) {
					
					int i=Integer.parseInt(new String(data).substring(0, 1));
					
					netframe.name=new String(data).substring(8,8+i);
					
				}else {
				
				netframe.Dialogue1=netframe.Dialogue2;
				
				netframe.Dialogue2=netframe.Dialogue3;
				
				netframe.Dialogue3=netframe.Dialogue4;
				
				netframe.Dialogue4=netframe.Dialogue5;
				
				netframe.Dialogue5=netframe.Dialogue6;
				
				netframe.Dialogue6=netframe.Dialogue7;
				
				netframe.Dialogue7=netframe.Dialogue8;
				
				netframe.Dialogue8=netframe.name+"   :"+new String(data);
				
				netframe.Dialogue8=netframe.Dialogue8.substring(0,netframe.str.length()+4+netframe.name.length());
				
				
				netframe.str="";
				
				
			}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

	
	public void play() {
		thread=new Thread(this);
		thread.start();
	}

}
