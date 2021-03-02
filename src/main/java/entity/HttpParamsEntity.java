package entity;

import java.util.List;
import java.util.Map;

public class HttpParamsEntity {
	
	private String ip;
	private String port;
	private String method;
	private List<Map<String, String>> pdrParams;
	private List<Map<String, String>> otherParams;
	private String url;
	private String params;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	private String path;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<Map<String, String>> getPdrParams() {
		return pdrParams;
	}
	public void setPdrParams(List<Map<String, String>> pdrParams) {
		this.pdrParams = pdrParams;
	}
	public List<Map<String, String>> getOtherParams() {
		return otherParams;
	}
	public void setOtherParams(List<Map<String, String>> otherParams) {
		this.otherParams = otherParams;
	}
	
}
