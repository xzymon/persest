package com.xzymon.servlet.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

public class StateBean {
	private List<Header> headers;
	private URLParts urlParts;
	private List<Cookie> cookies;
	private List<AttributeInfo> requestAttributes, sessionAttributes, applicationAttributes;
	private Map<String, String[]> parameters;
	
	public List<Header> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

	public URLParts getUrlParts() {
		return urlParts;
	}

	public void setUrlParts(URLParts urlParts) {
		this.urlParts = urlParts;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	public List<AttributeInfo> getRequestAttributes() {
		return requestAttributes;
	}

	public void setRequestAttributes(List<AttributeInfo> requestAttributes) {
		this.requestAttributes = requestAttributes;
	}

	public List<AttributeInfo> getSessionAttributes() {
		return sessionAttributes;
	}

	public void setSessionAttributes(List<AttributeInfo> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	public List<AttributeInfo> getApplicationAttributes() {
		return applicationAttributes;
	}

	public void setApplicationAttributes(List<AttributeInfo> applicationAttributes) {
		this.applicationAttributes = applicationAttributes;
	}

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
}
