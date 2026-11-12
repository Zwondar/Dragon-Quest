package netWork;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 2026-11-09 新增（优先级4）：网络/数据库配置读取工具。
 * 配置优先级：JVM 系统属性(-Dkey=value) > classpath 下的 config.properties > 代码默认值。
 * 这样服务器地址、端口、数据库账号不再硬编码在源码里。
 */
public final class NetConfig {

	private static final Properties PROPS = new Properties();

	static {
		try (InputStream in = NetConfig.class.getResourceAsStream("/config.properties")) {
			if (in != null) {
				PROPS.load(in);
			}
		} catch (IOException e) {
			System.err.println("读取 config.properties 失败: " + e.getMessage());
		}
	}

	private NetConfig() {
	}

	public static String get(String key, String defaultValue) {
		String value = System.getProperty(key);
		if (value == null) {
			value = PROPS.getProperty(key);
		}
		return value == null ? defaultValue : value;
	}

	public static int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(get(key, String.valueOf(defaultValue)).trim());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
