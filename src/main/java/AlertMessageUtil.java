import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import entity.HttpParamsEntity;
import utils.CheckUtil;

public class AlertMessageUtil {
	static Map<String, String> eventInfoMap = null;

	public static void main(String[] args) {
	}
	

	/**
	 * 处理“脚本参数”
	 * @param paramStr 脚本参数（pandora2.0页面配置）
	 * @param entity
	 * @param eventInfo
	 * @return 
	 */
	public static HttpParamsEntity handleRequestParams(String messageContent) {
		HttpParamsEntity entity = new HttpParamsEntity();
		
		JSONObject paramsObject = JSON.parseObject(messageContent);
		String paramsStr = paramsObject.getString("params");
		System.out.println("读取的脚本参数：" + paramsStr);
		
		eventInfoMap = JSONObject.toJavaObject(JSON.parseObject(messageContent),Map.class);
		System.out.println("eventInfoMap: " + eventInfoMap);
		
		JSONObject jsonObject = JSON.parseObject(paramsStr);
		String interfaceStr = jsonObject.getString("interface");
		
		JSONObject interfaceStrObj = JSON.parseObject(interfaceStr);
		String ip = interfaceStrObj.getString("ip");
		String port = interfaceStrObj.getString("port");
		String method = interfaceStrObj.getString("method").toLowerCase();
		String path = interfaceStrObj.getString("path");

		try {
			String params = getParams(interfaceStr);
			if(!params.isEmpty()) {
				params = params.substring(0, params.length()-1);
			}

			entity.setIp(ip);
			entity.setPort(port);
			entity.setMethod(method);
			entity.setPath(path);
			entity.setParams(params);
		} catch (UnsupportedEncodingException e) {
			System.out.println("处理脚本参数异常：" + e);
		}
		return entity;
	} 
	


	/**
	 * 获取请求参数
	 * @param eventInfo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static String getParams(String paramsStr) throws UnsupportedEncodingException {
		JSONObject paramObject = JSON.parseObject(paramsStr);
		
		JSONArray pdrParamsArray = paramObject.getJSONArray("pdrParams");
		Map<String, String> map = null;
		
		//从EventInfo对象中找到脚本参数的值
		StringBuilder requestParams = new StringBuilder();
		for (int i = 0; i < pdrParamsArray.size(); i++) {
			map = JSONObject.toJavaObject(pdrParamsArray.getJSONObject(i), Map.class);

			Iterator entries = map.entrySet().iterator();
			Iterator entriesEventInfoMap = eventInfoMap.entrySet().iterator();
			while(entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				
				while(entriesEventInfoMap.hasNext()) {
					StringBuilder sb = new StringBuilder();
					Map.Entry entriesEventInfoEntry = (Map.Entry) entriesEventInfoMap.next();
					if (entry.getValue().equals(entriesEventInfoEntry.getKey().toString())) {
						// [深农商单独处理]
						if ("eventDetails".equals(entry.getValue())) {
							String eventDetails = entriesEventInfoEntry.getValue().toString();
							sb.append(entry.getKey()).append("=").append(URLEncoder.encode(eventDetails, "UTF-8")).append("&");
						} else if ("eventSubject".equals(entry.getValue())){
							sb.append(getSubjectName(entry.getKey().toString(), entriesEventInfoEntry.getValue().toString())).append("&");
						} else {
							sb.append(entry.getKey()).append("=")
									.append(URLEncoder.encode(entriesEventInfoEntry.getValue().toString())).append("&");
						}
						
					}
					if(!sb.toString().isEmpty()) {
						requestParams = requestParams.append(sb);
					}
				}
			}
		}

		//处理其他参数
		JSONArray otherParamsArray = paramObject.getJSONArray("otherParams");
		Map<String, String> otherParamMap = null;
		for(int i = 0; i < otherParamsArray.size(); i++) {
			StringBuilder otherSB = new StringBuilder();
			otherParamMap = JSONObject.toJavaObject(otherParamsArray.getJSONObject(i),Map.class);
			Iterator entryOtherParam = otherParamMap.entrySet().iterator();
			while(entryOtherParam.hasNext()) {
				Map.Entry entry = (Map.Entry) entryOtherParam.next();
				
				//[深农商单独处理 attachement = $alartname+$eventtime]
				if("attachement".equals(entry.getKey())) {
					otherSB.append(entry.getKey()).append("=").append(URLEncoder.encode(getPDRAttachementValue(entry.getValue().toString()), "UTF-8")).append("&");
				} else {
					otherSB.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
			}
			requestParams = requestParams.append(otherSB);
		}

		return requestParams.toString();
	}
	
	//深农商处理subjectName
	public static String getSubjectName(String key,String subjectName) throws UnsupportedEncodingException {
		
		String[] arr = subjectName.split(",");
		StringBuilder requestParams = new StringBuilder();
		
		for(int i = 0 ; i < arr.length; i++) {
			String result = arr[i].trim();
			int index = result.indexOf("=");
			requestParams.append(result.substring(result.indexOf("=")+1, result.length())).append(" ");
		}
		return key +"="+ URLEncoder.encode(requestParams.toString(), "UTF-8");
	}
	
	/**
	 * 深农商处理 attachement<br>
	 * {"attachement":"evenTtime,eventSubject"} <br>
	 * \n-前后加个空格 就是 "value1 \n- value2"<br>
	 * @param attachementValue 
	 * @return attachStr
	 * 
	 */
	public static String getPDRAttachementValue(String attachementValue) {
		if(attachementValue.isEmpty()) return "";
		
		StringBuilder sb = new StringBuilder();
		String attachStr = "";
		
		String[] arr = attachementValue.split(",");
		for(int i = 0; i < arr.length; i++) {
			Iterator entriesEventInfoMap = eventInfoMap.entrySet().iterator();
			while(entriesEventInfoMap.hasNext()) {
				Map.Entry entriesEventInfoEntry = (Map.Entry) entriesEventInfoMap.next();
				if(arr[i].equals(entriesEventInfoEntry.getKey())) {
					sb.append(entriesEventInfoEntry.getValue()).append(" ").append("\n-").append(" ");
				}
			}
		}
		attachStr =  sb.toString().substring(0, sb.toString().length()-2);
		System.out.println("获取的attachement :" + attachStr.trim());
		return attachStr.trim();
	}

}
