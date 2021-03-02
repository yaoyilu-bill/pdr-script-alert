import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

// 脚本通过Stdin接受的参数定义
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EventInfo{
  @JsonProperty("eventDisplayId")
  private String eventDisplayId;
  @JsonProperty("eventName")
  private String eventName;
  @JsonProperty("eventDescription")
  private String eventDescription;
  @JsonProperty("alertService")
  private String alertService;
  @JsonProperty("alertLevel")
  private String alertLevel;
  @JsonProperty("eventTime")
  private String eventTime;
  @JsonProperty("alertSourceType")
  private String alertSourceType;
  @JsonProperty("alertName")
  private String alertName;
  @JsonProperty("alertDescription")
  private String alertDescription;
  @JsonProperty("alertInterval")
  private String alertInterval;
  @JsonProperty("alertConditionCnt")
  private String alertConditionCnt;
  @JsonProperty("alertExecuteCount")
  private String alertExecuteCount;
  @JsonProperty("eventDetails")
  private String eventDetails;
  @JsonProperty("eventId")
  private String eventId;
  @JsonProperty("operationType")
  private String operationType;
  @JsonProperty("operateNote")
  private String operateNote;
  @JsonProperty("phoenixHost")
  private String phoenixHost;
  @JsonProperty("params")
  private Map<String, String> params;
  @JsonProperty("additionContent")
  private List<Map<String, String>> additionContent;
  @JsonProperty("userName")
  private String userName;
  @JsonProperty("realUserName")
  private String realUserName;

  

public EventInfo(){}

  public String getEventDisplayId() {
    return eventDisplayId;
  }

  public void setEventDisplayId(String eventDisplayId) {
    this.eventDisplayId = eventDisplayId;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getEventDescription() {
    return eventDescription;
  }

  public void setEventDescription(String eventDescription) {
    this.eventDescription = eventDescription;
  }

  public String getAlertService() {
    return alertService;
  }

  public void setAlertService(String alertService) {
    this.alertService = alertService;
  }

  public String getAlertLevel() {
    return alertLevel;
  }

  public void setAlertLevel(String alertLevel) {
    this.alertLevel = alertLevel;
  }

  public String getEventTime() {
    return eventTime;
  }

  public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
  }

  public String getAlertSourceType() {
    return alertSourceType;
  }

  public void setAlertSourceType(String alertSourceType) {
    this.alertSourceType = alertSourceType;
  }

  public String getAlertName() {
    return alertName;
  }

  public void setAlertName(String alertName) {
    this.alertName = alertName;
  }

  public String getAlertDescription() {
    return alertDescription;
  }

  public void setAlertDescription(String alertDescription) {
    this.alertDescription = alertDescription;
  }

  public String getAlertInterval() {
    return alertInterval;
  }

  public void setAlertInterval(String alertInterval) {
    this.alertInterval = alertInterval;
  }

  public String getAlertConditionCnt() {
    return alertConditionCnt;
  }

  public void setAlertConditionCnt(String alertConditionCnt) {
    this.alertConditionCnt = alertConditionCnt;
  }

  public String getAlertExecuteCount() {
    return alertExecuteCount;
  }

  public void setAlertExecuteCount(String alertExecuteCount) {
    this.alertExecuteCount = alertExecuteCount;
  }

  public String getEventDetails() {
    return eventDetails;
  }

  public void setEventDetails(String eventDetails) {
    this.eventDetails = eventDetails;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  public String getOperateNote() {
    return operateNote;
  }

  public void setOperateNote(String operateNote) {
    this.operateNote = operateNote;
  }

  public String getPhoenixHost() {
    return phoenixHost;
  }

  public void setPhoenixHost(String phoenixHost) {
    this.phoenixHost = phoenixHost;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public void setParams(Map<String, String> params) {
    this.params = params;
  }

  public List<Map<String, String>> getAdditionContent() {
    return additionContent;
  }

  public void setAdditionContent(List<Map<String, String>> additionContent) {
    this.additionContent = additionContent;
  }
  
  	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealUserName() {
		return realUserName;
	}

	public void setRealUserName(String realUserName) {
		this.realUserName = realUserName;
	}
}
