import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * 读取jar同级目录config.properties参数配置
 * @author ylyao
 * @create_time：下午3:18:36
 * @version 1.0
 */
public class ConfigUtil {

	public static void main(String[] args) throws IOException {
		getIP();
	}
	
	public static String getIP() {
		
		String ip = "";
		try {
			
			String configPath = new File(System.getProperty("java.class.path")).getParent() + "/config.properties";
			System.out.println("获取config.properties目录为：" + configPath + "\n ");
			
			Properties props = new Properties();
			props.load(new FileInputStream(configPath));
			ip = props.getProperty("itsmIP");
			System.out.println("获取config.properties配置itsmIP: " + ip + " \n");
			
		} catch (Exception e) {
			System.out.println("读取config.properties文件异常：" + e.getMessage());
		}
		
		return ip;
	
	}

}
