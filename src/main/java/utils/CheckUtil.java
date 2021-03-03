package utils;

import com.alibaba.fastjson.JSONObject;

public class CheckUtil {

	public static void main(String[] args) {
		
	}
	
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}
	
	/**
	 * check jsonStr is a real json
	 * @param jsonStr
	 * @return
	 */
	public static boolean isJson(String jsonStr) {
		try {
	        JSONObject.parseObject(jsonStr);

			return true;
		} catch (Exception e){
			return false;
		}
		
	}

}
