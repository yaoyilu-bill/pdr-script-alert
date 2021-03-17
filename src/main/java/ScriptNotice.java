import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.HttpParamsEntity;
import utils.CheckUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 参数以Stdin获取，返回以Stdout输出
 */
public class ScriptNotice {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
		System.out.println("脚本启动！！！");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = in.readLine();
		System.out.println("读取告警内容为：" + line);
		
//		String line = "[{\"eventDisplayId\":\"53\",\"eventName\":\"test_eventSubject\",\"eventDescription\":\"测试告警对象\",\"alertLevel\":\"灾难\",\"eventTime\":\"2021-03-17 18:22:00\",\"alertSourceType\":\"日志告警\",\"alertName\":\"test_eventSubject\",\"alertDescription\":\"测试告警对象\",\"alertInterval\":\"每分钟\",\"alertExecuteCount\":\"711\",\"eventId\":\"69fc53f6-9635-4725-8962-04c498ff6ecd\",\"eventSubject\":\"登录结果=success, 登录主机=jjh2239, count=1\",\"phoenixHost\":\"http://100.100.69.143:9200\",\"userName\":\"ylyao\",\"realUserName\":null,\"operationType\":\"notice\",\"eventDetails\":\"> 1. 条件1, 级别0: 字段【count】异常, 当前值【1.0】大于等于 1.0。\\n\",\"alertConditionCnt\":\"1\",\"operateNote\":\"\",\"params\":{\"interface\":\"{\\\"ip\\\":\\\"100.100.69.143\\\",\\\"port\\\":\\\"8081\\\",\\\"method\\\":\\\"post\\\",\\\"path\\\":\\\"event/post\\\",\\\"pdrParams\\\":[{\\\"content\\\":\\\"eventDetails\\\"},{\\\"object\\\":\\\"eventSubject\\\"}],\\\"otherParams\\\":[{\\\"source\\\":\\\"Test\\\"},{\\\"group\\\":\\\"aaa\\\"},{\\\"attachement\\\":\\\"eventName,userName\\\"}]}\"},\"additionContent\":[{\"count\":\"46\",\"TIME\":\"2021-03-17 18:22:00\",\"附加内容名称\":\"testCount\",\"SPL\":\"repo=\\\"*linux_audit\\\" \\\"su\\\" | rex field=_raw \\\"^.*pid=(?<pid>\\\\d+).*acct=\\\\\\\"(?<account>.*?)\\\\\\\".*hostname=(?<hostname>.*?)\\\\s.*res=(?<res>.*?)\\\\'\\\" | where isNotNull(account) | dedup pid | stats count() by _time,hostname,res | fields hostname,res,count | sort by _time | rename _time as 操作时间,hostname as 登录主机,res as 登录结果 | stats count()  | limit 500\"}]}]";

		List<EventInfo> res = readList(line, EventInfo.class);
		String message = getMessageContent(res);
		System.out.println("读取告警参数为：" + message);
		
		if (res.isEmpty()) {
			System.out.println("event list is empty");
			return;
		}

		JSONArray lineArray = JSON.parseArray(line);
		for(int i = 0; i < lineArray.size(); i++) {
			
			if(!CheckUtil.isJson(lineArray.get(i).toString())) {
				System.out.println("不是有效的json，请检查脚本参数...");
				break;
			} 
			
			HttpParamsEntity entity = new HttpParamsEntity();
			for (EventInfo event : res) {
				
				entity = AlertMessageUtil.handleRequestParams(lineArray.get(i).toString());
				if(!CheckUtil.isEmpty(entity)) {
				
					String ip = entity.getIp();
					String port = entity.getPort();
					String paramStr = entity.getParams();
					String method = entity.getMethod();
					String path = entity.getPath();

					String url = "http://" + ip + ":" + port + "/" + path;
					System.out.println("生成的url: " + url + ",paramStr: " + paramStr +",method: " + method);
					
					String result = "";
					if("get".equals(method)) {
						result = HttpUtil.sendGet(url, paramStr);
					} else if("post".equals(method)){
						result = HttpUtil.sendPost(url, paramStr);
					}
					System.out.println("请求：" + url + ",参数为：" + paramStr + ",响应结果为：" + result);
				}
				
			}
		}
		
		System.out.println("脚本结束！！！");
	}

	/**
	 * @param res
	 * @return 
	 */
	public static String getMessageContent(List<EventInfo> res) {
		StringBuilder sb = new StringBuilder();
		for (EventInfo info : res) {
			sb.append("[Event eventDisplayId: " + info.getEventDisplayId() + "],") // 事件序列id,用以展示用的
					.append("[Event eventName: " + info.getEventName() + "],") // 事件名称
					.append("[Event eventDetails: " + info.getEventDetails() + "],") // 事件详情
					.append("[Event eventId: " + info.getEventId() + "],") // 事件id，唯一标识
					.append("[Event eventTime: " + info.getEventTime() + "],") // 事件产生时间
					.append("[Event eventDescription: " + info.getEventDescription() + "],") // 事件描述

					.append("[alert alertService: " + info.getAlertService() + "],") // 告警服务， 废弃
					.append("[alert alertLevel: " + info.getAlertLevel() + "],") // 告警级别
					.append("[alert alertSourceType: " + info.getAlertSourceType() + "],") // 告警来源
					.append("[alert alertName: " + info.getAlertName() + "],") // 告警名称
					.append("[alert alertDescription : " + info.getAlertDescription() + "],") // 告警描述
					.append("[alert alertInterval: " + info.getAlertInterval() + "],") // 告警频率
					.append("[alert alertConditionCnt: " + info.getAlertConditionCnt() + "],") // 告警条件数
					.append("[alert alertExecuteCount: " + info.getAlertExecuteCount() + "],") // 告警执行次数

					.append("[operationType: " + info.getOperationType() + "],") // 操作类型
					.append("[operateNote: " + info.getOperateNote() + "],") // 操作备注
					.append("[PhoenixHost: " + info.getPhoenixHost() + "],") // pandora服务host
					.append("[userName: " + info.getUserName() + "],") // 告警创建者的用户名
					.append("[realUserName: " + info.getRealUserName() + "],") // 真实用户名
					.append("[params: " + info.getParams() + "],") // web页面配置的自定义参数
					.append("[additionContent: " + info.getAdditionContent() + "],")
					.append("[eventSubject: "+ info.getEventSubject() +"]"); // 附加内容

			for (Map.Entry<String, String> entry : info.getParams().entrySet()) {
				sb.append("[" + entry.getKey() + ": " + entry.getValue() + "], ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static <T> List<T> readList(String json, Class<T> tClass) throws IOException {
		List<T> result = null;
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);
			result = mapper.readValue(json, javaType);
		} catch (JsonMappingException | JsonParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

}
