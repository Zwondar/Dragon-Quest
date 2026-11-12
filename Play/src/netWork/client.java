package netWork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import frame.Startgame;

/**
 * 2026-11-09 重写（优先级4）：
 * 1) 服务器地址/端口改为从 config.properties 读取，不再写死 192.168.221.150；
 * 2) 删除收到 "qqwees" 就修改 Slime.HP、收到 "q1ww2e" 就把内容写入 src/netWork/patch.java
 *    的远程后门（原逻辑可被任意 UDP 包利用，属于远程代码/文件写入风险）；
 * 3) 协议改为 [4 字节长度][UTF-8 负载]，避免固定 1024 缓冲带来的字符串越界问题；
 * 4) 单个坏包/异常不再杀死整个接收线程。
 */
public class client implements Runnable{

	// 2026-11-09 新增（优先级4）：单个 UDP 包的最大长度，防止异常数据导致越界。
	private static final int MAX_PACKET = 8192;

	public static Thread thread;

	private final int serverPort;

	private final int clientPort;

	private DatagramSocket sendSocket;

	private DatagramSocket recvSocket;

	private InetAddress server;

	public client() {

		serverPort = NetConfig.getInt("server.port", 15588);

		clientPort = NetConfig.getInt("client.port", 15589);

		try {

			sendSocket = new DatagramSocket();

			server = InetAddress.getByName(NetConfig.get("server.host", "127.0.0.1"));

			try {
				recvSocket = new DatagramSocket(clientPort);
			} catch (SocketException e) {
				// 2026-11-09 修复（优先级4）：原代码端口冲突时直接抛异常。
				// 这里降级为"只能发送、不能接收"，并明确提示，避免整个游戏启动失败。
				System.err.println("接收端口 " + clientPort + " 被占用，公屏聊天将无法接收消息: " + e.getMessage());
				recvSocket = null;
			}

		} catch (IOException e) {
			System.err.println("网络初始化失败，公屏聊天不可用: " + e.getMessage());
		}
	}

	public void send() {

		String payload;

		if(Startgame.datacontrol==false) {

			// 聊天消息：CHAT:<昵称>:<内容>
			payload = "CHAT:" + netframe.name + ":" + netframe.str;

		}else {

			// 注册昵称：REGISTER:<昵称>
			payload = "REGISTER:" + netframe.name;

		}

		sendPayload(payload);

	}

	private void sendPayload(String payload) {

		if(sendSocket==null||server==null) return;

		try {
			byte[] data = payload.getBytes(StandardCharsets.UTF_8);

			// 2026-11-09 新增（优先级4）：4 字节长度前缀，长度不再依赖单个字符。
			byte[] packetData = ByteBuffer.allocate(4 + data.length).putInt(data.length).put(data).array();

			sendSocket.send(new DatagramPacket(packetData, packetData.length, server, serverPort));

		} catch (IOException e) {
			System.err.println("发送聊天数据失败: " + e.getMessage());
		}
	}

	public void run() {//接收方法

		if(recvSocket==null) return;

		byte[] buffer = new byte[MAX_PACKET];

		while(true) {

			try {

				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

				recvSocket.receive(packet);

				String payload = decode(packet);

				if(payload==null) continue;

				// 2026-11-09 重写（优先级4）：只接受聊天/系统消息，不再根据网络数据修改游戏状态。
				if(payload.startsWith("CHAT:")) {
					netframe.pushMessage(payload.substring(5));
				}else if(payload.startsWith("INFO:")) {
					netframe.pushMessage("[系统] " + payload.substring(5));
				}

			} catch (IOException e) {
				// 2026-11-09 修复（优先级4）：单个坏包不应终止接收线程。
				System.err.println("接收聊天数据失败: " + e.getMessage());
			} catch (RuntimeException e) {
				// 2026-11-09 修复（优先级4）：原未捕获的运行时异常（如 StringIndexOutOfBounds）
				// 会直接杀死接收线程。这里记录后继续循环。
				e.printStackTrace();
			}
		}
	}

	// 2026-11-09 新增（优先级4）：解析 [4 字节长度][负载]，并做边界校验。
	private static String decode(DatagramPacket packet) {

		int length = packet.getLength();

		if(length < 4) return null;

		byte[] data = packet.getData();

		int offset = packet.getOffset();

		int payloadLength = ByteBuffer.wrap(data, offset, 4).getInt();

		if(payloadLength < 0 || payloadLength > length - 4) return null;

		return new String(data, offset + 4, payloadLength, StandardCharsets.UTF_8);
	}

	public void play() {
		thread=new Thread(this);
		thread.start();
	}

}
