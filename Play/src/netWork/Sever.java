package netWork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

public class Sever implements  Runnable{
	
	static Thread thread;
	
  public InetAddress in;
  
  public InetAddress client;
  
  public static DatagramSocket dsr;
  
  public static DatagramSocket dss;
  
  public static boolean control;
  
  static Connection con;
  
  static java.sql.Statement stmt;
  
  byte [] buffer=new byte[1024];
  
  static Sever s;
  
  public Sever() {
	  try {
		  
		 control=false;
		
		dsr=new DatagramSocket(15588);
		
		dss=new DatagramSocket();
		
		in=InetAddress.getByName("255.255.255.255");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url="jdbc:mysql://127.0.0.1:3306/text";
		
		String name="root";
		
		String password="123456";
		
		con=DriverManager.getConnection(url, name, password);
		
		stmt=con.createStatement();
		
		
	} catch (SocketException | UnknownHostException | ClassNotFoundException | SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
  }
  
  public void getdata(String ip ,String gamename) {
	  
	  try {
		
		stmt.executeUpdate("insert into game(ip,name) values('"+ip+"','"+gamename+"')");
		
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	  
  }
  
  public void send() {
	  
	  DatagramPacket dp=new DatagramPacket(buffer,buffer.length,in,15589);
	  
	  try {
		  
		dss.send(dp);
		
		
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	  
  }
 
  public void receive() {
	  
	  byte[] getdata=new byte[1024];
	  
	  try {
		
		DatagramPacket dp=new DatagramPacket(getdata, getdata.length);
		
		dsr.receive(dp);
		
		buffer=dp.getData();
		
		if(new String(buffer).substring(1,8).equals("192.003")) {
			
			int length=Integer.parseInt(new String(buffer).substring(0, 1));
			
			client=dp.getAddress();
			String ip=client.getHostAddress();
			String name=new String(buffer).substring(8,8+length);
			
			getdata(ip, name);
			
			control=true;
			
		}
		
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	  
	  
  }
  
 /* public static void main(String[] args) {
	
	  s=new Sever();
	  
	  s.play();
	  
	  while(true) {
		  
	  s.receive();
	  if(control==false)
		  
	  s.send();
	  
	  control=false;
	  }
   }
*/
@Override
public void run() {
	
	while(true) {
		
		String ip;
		
		String name;
		
		ResultSet rs;
		try {
			rs = stmt.executeQuery("select * from game");
			while(rs.next()) {
				
				byte send [ ];
				
				ip=rs.getString(1);
				
				name=rs.getString(2);
				
				send=(String.valueOf(name.length())+"192.003"+name).getBytes();
				
				InetAddress i=InetAddress.getByName(ip);
				
				DatagramPacket dp=new DatagramPacket(send,send.length,i,15589);
				
				dss.send(dp);
				
			}
			
		} catch (SQLException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		
	try {
		
		thread.sleep(10000);
	} catch (InterruptedException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	  
	}
   }

public void play() {
	thread=new Thread(this);
	thread.start();
}
}
