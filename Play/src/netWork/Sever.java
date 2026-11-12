package netWork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2026-11-09 重写（优先级4）：
 * 1) 数据库地址/账号从 config.properties 读取，不再硬编码 root/123456；
 * 2) 使用 PreparedStatement 修复 SQL 注入；
 * 3) 改用 [4 字节长度][UTF-8 负载] 协议，并真正在 run() 中接收消息后转发
 *    （原 run() 从不调用 receive()，所以聊天/注册实际上不工作）；
 * 4) 数据库连接失败时降级为"内存转发模式"，聊天仍可用。
 */
public class Sever implements Runnable{

	private static final int MAX_PACKET = 8192;

	// 2026-11-09 修复（优先级4）：记录最近通信过的客户端地址，用于单播转发。
	// 原代码使用 255.255.255.255 广播，很多网络/防火墙下收不到。
	private final Set<InetAddress> clients = ConcurrentHashMap.newKeySet();

	private final DatagramSocket socket;

	private final int clientPort;

	private Connection con;

	private static Thread thread;

	public Sever() {

		clientPort = NetConfig.getInt("client.port", 15589);

		int listenPort = NetConfig.getInt("server.port", 15588);

		DatagramSocket s = null;

		try {
			s = new DatagramSocket(listenPort);
		} catch (IOException e) {
			System.err.println("聊天服务器端口 " + listenPort + " 绑定失败: " + e.getMessage());
		}

		socket = s;

		initDatabase();
	}

	// 2026-11-09 修复（优先级4）：数据库配置外部化 + 连接失败时降级，不影响聊天转发。
	private void initDatabase() {

		try {
			String url = NetConfig.get("db.url",
					"jdbc:mysql://127.0.0.1:3306/text?useUnicode=true&characterEncoding=utf8");
			String user = NetConfig.get("db.user", "root");
			String password = NetConfig.get("db.password", "");

			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, password);

			// 2026-11-09 新增（优先级4）：自动建表，减少手动准备。
			try (Statement stmt = con.createStatement()) {
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS game ("
						+ "ip VARCHAR(64), name VARCHAR(64))");
			}

		} catch (ClassNotFoundException e) {
			System.err.println("未找到 MySQL 驱动，服务器将以内存模式运行（聊天可用，注册不写库）。"
					+ "请把 mysql-connector-j 的 jar 加入 classpath。");
		} catch (SQLException e) {
			System.err.println("数据库连接失败，服务器将以内存模式运行: " + e.getMessage());
		}
	}

	// 2026-11-09 修复（优先级4）：原为字符串拼接 SQL，存在注入风险；改用 PreparedStatement。
	public void register(String ip, String gamename) {

		if(con==null) return;

		try (PreparedStatement ps = con.prepareStatement("INSERT INTO game(ip,name) VALUES(?,?)")) {
			ps.setString(1, ip);
			ps.setString(2, gamename);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void sendTo(InetAddress address, String payload) {

		if(socket==null||address==null) return;

		try {
			byte[] data = payload.getBytes(StandardCharsets.UTF_8);
			byte[] packetData = ByteBuffer.allocate(4 + data.length).putInt(data.length).put(data).array();
			socket.send(new DatagramPacket(packetData, packetData.length, address, clientPort));
		} catch (IOException e) {
			System.err.println("转发聊天消息失败: " + e.getMessage());
		}
	}

	private void broadcast(String payload) {
		for (InetAddress address : clients) {
			sendTo(address, payload);
		}
	}

	private static String decode(DatagramPacket packet) {

		int length = packet.getLength();

		if(length < 4) return null;

		byte[] data = packet.getData();

		int offset = packet.getOffset();

		int payloadLength = ByteBuffer.wrap(data, offset, 4).getInt();

		if(payloadLength < 0 || payloadLength > length - 4) return null;

		return new String(data, offset + 4, payloadLength, StandardCharsets.UTF_8);
	}

	@Override
	public void run() {

		if(socket==null) return;

		byte[] buffer = new byte[MAX_PACKET];

		while(true) {

			try {

				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

				socket.receive(packet);

				InetAddress address = packet.getAddress();

				clients.add(address);

				String payload = decode(packet);

				if(payload==null) continue;

				if(payload.startsWith("REGISTER:")) {

					String name = payload.substring(9).trim();

					// 2026-11-09 新增（优先级4）：昵称长度校验，避免超长/空昵称。
					if(name.isEmpty()) name = "游客";
					if(name.length() > 16) name = name.substring(0, 16);

					register(address.getHostAddress(), name);

					sendTo(address, "INFO:注册成功，昵称：" + name);

				}else if(payload.startsWith("CHAT:")) {

					String body = payload.substring(5);

					int split = body.indexOf(':');

					if(split <= 0) continue;

					String name = body.substring(0, split);
					String message = body.substring(split + 1);

					if(name.length() > 16) name = name.substring(0, 16);
					if(message.length() > 200) message = message.substring(0, 200);

					// 2026-11-09 修复（优先级4）：把聊天内容转发给所有已知客户端。
					broadcast("CHAT:" + name + ":" + message);
				}

			} catch (IOException e) {
				// 2026-11-09 修复（优先级4）：单个坏包不再导致服务器循环中断。
				System.err.println("服务器接收消息失败: " + e.getMessage());
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	public void play() {
		thread=new Thread(this);
		thread.start();
	}

	// 2026-11-09 修复（优先级4）：原来 main 被整段注释，服务器无法直接启动；现在提供入口。
	public static void main(String[] args) {
		new Sever().play();
	}
}
