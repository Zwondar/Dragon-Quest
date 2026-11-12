package netWork;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import frame.Startgame;
import image.getimage;

public class netframe extends JDialog{

	private static final long serialVersionUID = 1L;

	JPanel p;

	// 2026-11-09 修复（优先级2）：这些字段被 EDT 与网络接收线程同时访问，声明为 volatile 保证可见性。
	public static volatile String str="";

	public static volatile String name="游客";

	public  JTextArea jta=new JTextArea();

	public  JButton jb=new JButton("发送");

	public  JButton cb=new JButton("创建玩家");

	public static volatile String Dialogue1="";

	public static volatile String Dialogue2="";

	public static volatile String Dialogue3="";

    public static volatile String Dialogue4="";

	public static volatile String Dialogue5="";

	public static volatile String Dialogue6="";

    public static volatile String Dialogue7="";

	public static volatile String Dialogue8="";

	// 2026-11-09 修复（优先级1）：复用离屏缓冲，避免每次 paint 都 createImage。
	private Image buffer;

	// 2026-11-09 修复（优先级2）：只保留一个聊天窗口实例，重复打开时置前而不是新建。
	private static netframe instance;

	public static synchronized void showChat(Frame owner) {
		if(instance!=null&&instance.isDisplayable()) {
			instance.toFront();
			instance.requestFocus();
			return;
		}
		instance=new netframe(owner,false);
	}

	// 2026-11-09 新增（优先级4）：供 client 接收线程推送消息，并安全地刷新到窗口。
	public static synchronized void pushMessage(String message) {
		Dialogue1=Dialogue2;

		Dialogue2=Dialogue3;

		Dialogue3=Dialogue4;

		Dialogue4=Dialogue5;

		Dialogue5=Dialogue6;

		Dialogue6=Dialogue7;

		Dialogue7=Dialogue8;

		Dialogue8=message;

		if(instance!=null) instance.repaint();
	}

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

				// 2026-11-09 修复（优先级4）：空消息不再发送。
				String text=jta.getText().trim();

				if(text.isEmpty()) return;

				str=text;

				jta.setText("");

				Startgame.c.send();

				repaint();

			}
		});

		cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Startgame.datacontrol=true;

				// 2026-11-09 修复（优先级4）：限制昵称长度，避免超长昵称破坏协议。
				String newName=jta.getText().trim();

				if(newName.isEmpty()) newName="游客";

				if(newName.length()>16) newName=newName.substring(0,16);

                name=newName;

				jta.setText("");

				Startgame.c.send();

				pushMessage("[系统] 昵称已设置为：" + name);

				repaint();

			}
		});

		p.add(cb);

		p.add(jb);

		p.add(jta);

		setResizable(false);

		// 2026-11-09 修复（优先级2）：关闭窗口时释放资源，避免窗口对象泄漏。
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setVisible(true);


	}

	@Override
	public void paint(Graphics g) {

		// 2026-11-09 修复（优先级1）：离屏缓冲只创建一次并复用。
		if(buffer==null) {
			buffer=createImage(Startgame.width,Startgame.height);
		}

		Graphics ig=buffer.getGraphics();

		paintself(ig);

		g.drawImage(buffer,0,0,Startgame.width,Startgame.height/5*4,this);

		ig.dispose();

	}

	public void paintself(Graphics g) {

		 g.setColor(Color.BLACK);

			g.setFont(new Font("黑体", Font.BOLD, 30));

			 if(getimage.netWork!=null) {
				 g.drawImage(getimage.netWork,0,0,Startgame.width,Startgame.height,this);
			 }

			g.drawString("请输入内容   ：",Startgame.width/100,Startgame.height/10*9);


			g.drawString(Dialogue1, Startgame.width/100, Startgame.height/10);

			g.drawString(Dialogue2, Startgame.width/100, Startgame.height/10*2);

			g.drawString(Dialogue3, Startgame.width/100, Startgame.height/10*3);

			g.drawString(Dialogue4, Startgame.width/100, Startgame.height/10*4);

			g.drawString(Dialogue5, Startgame.width/100, Startgame.height/10*5);

			g.drawString(Dialogue6, Startgame.width/100, Startgame.height/10*6);

			g.drawString(Dialogue7, Startgame.width/100, Startgame.height/10*7);

			g.drawString(Dialogue8, Startgame.width/100, Startgame.height/10*8);
	}

}
