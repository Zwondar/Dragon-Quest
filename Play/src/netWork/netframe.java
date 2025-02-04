package netWork;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import frame.Startgame;
import image.getimage;

public class netframe extends JDialog{
	
	JPanel p;
	
	public static String str="";
	
	public static String name="游客";
	
	public  JTextArea jta=new JTextArea();
	
	public  JButton jb=new JButton("发送");
	
	public  JButton cb=new JButton("创建玩家");
	
	public static String Dialogue1="";
	
	public static String Dialogue2="";
	
	public static String Dialogue3="";
	
    public static String Dialogue4="";
	
	public static String Dialogue5="";
	
	public static String Dialogue6="";
	
    public static String Dialogue7="";
	
	public static String Dialogue8="";
	
	public netframe(Frame f,boolean b) {
		super(f,b);
		
p=(JPanel)getContentPane();	
		
		p.setLayout(null);
		
		p.setOpaque(false);
	
		setBounds(0, 0, Startgame.width, Startgame.height);
		
		jta.setBounds(0, Startgame.height/5*4, Startgame.width/10*9,Startgame.height/5);
		
		jb.setBounds(Startgame.width/10*9,Startgame.height/5*4,Startgame.width/10,Startgame.height/20);
		
		cb.setBounds(Startgame.width/10*9,Startgame.height/10*9,Startgame.width/10,Startgame.height/20);
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Startgame.datacontrol=false;
				
				str=jta.getText();
				
				jta.setText("");
				
					Startgame.c.send();
					
					
				
				repaint();
				
			}
		});
		
		cb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Startgame.datacontrol=true;
				
                name=jta.getText();
				
				jta.setText("");
				
				Startgame.c.send();
				
				repaint();
				
			}
		});
		
		p.add(cb);
		
		p.add(jb);
		
		p.add(jta);
		
		setResizable(false);
		
		setVisible(true);
		
		
	}

	@Override
	public void paint(Graphics g) {
		
		Image image=createImage(Startgame.width,Startgame.height);
		
		Graphics ig=image.getGraphics();
		
		paintself(ig);
		
		g.drawImage(image,0,0,Startgame.width,Startgame.height/5*4,this);
	
	}
	
	public void paintself(Graphics g) {
		 g.setColor(Color.BLACK);
			
			g.setFont(new Font("黑体", Font.BOLD, 30));
			 
			g.drawImage(getimage.netWork,0,0,Startgame.width,Startgame.height,this);
			
			g.drawString("请输入内容   ：",Startgame.width/100,Startgame.height/10*9);
			
			
			g.drawString(Dialogue1, Startgame.width/100,Startgame.height/10);
			
			g.drawString(Dialogue2, Startgame.width/100,Startgame.height/10*2);
			
			g.drawString(Dialogue3, Startgame.width/100,Startgame.height/10*3);
			
			g.drawString(Dialogue4, Startgame.width/100,Startgame.height/10*4);
			
			g.drawString(Dialogue5, Startgame.width/100,Startgame.height/10*5);
			
			g.drawString(Dialogue6, Startgame.width/100,Startgame.height/10*6);
			
			g.drawString(Dialogue7, Startgame.width/100,Startgame.height/10*7);
			
			g.drawString(Dialogue8, Startgame.width/100,Startgame.height/10*8);
	}

}

