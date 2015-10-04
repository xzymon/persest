package com.xzymon.servlet.util;

public class URLParts {
	private String protocol, serverName, servletPath, queryString;
	private Number port;
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServletPath() {
		return servletPath;
	}
	public void setServletPath(String serverPath) {
		this.servletPath = serverPath;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public Number getPort() {
		return port;
	}
	public void setPort(Number port) {
		this.port = port;
	}
	
	
}
