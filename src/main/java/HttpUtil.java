import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author ylyao
 * @create_time：2021/01/31 15:16:11
 * @version 1.0
 */
public class HttpUtil {

	public static void main(String[] args) {

	}
	
	/**
	 * 发送Get请求 
	 * @param url
	 * @param param
	 * @return 响应报文
	 */
	public static String sendGet(String url, String param) {
		
		String urlName = url + "?" + param;
		String result = "";

		try {
			URL realUrl = new URL(urlName);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			conn.connect();
//			Map<String,List<String>> map = conn.getHeaderFields();

//			for (String key : map.keySet()) {
//				System.out.println(key + "-->" + map.get(key));
//			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

			
		} catch (Exception e ) {
			System.out.println("发送Get请求异常" + e);
		} 
		
		return result;
	}
	
	
	/**
	 * Post 请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPost(String url, String param) {
		String result = "";
		try{
			URL realUrl = new URL(url);
			//打开和URL之间的连接
			URLConnection conn =  realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			out.print(param);
			//flush输出流的缓冲
			out.flush();
			// 定义 BufferedReader输入流来读取URL的响应
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常" + e);
		}
		return result;
	}
	
}
