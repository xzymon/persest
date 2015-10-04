package com.xzymon.servlet.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AppStateOutputServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/AppStateOutputServlet", "/state" })
public class AppStateOutputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppStateOutputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StateBean stateBean = new StateBean();
		
		stateBean.setHeaders(getHeaders(request));
		
		stateBean.setUrlParts(createURLParts(request));
		
		stateBean.setCookies(getCookies(request));
		
		stateBean.setApplicationAttributes(getApplicationAttributes(request));
		stateBean.setSessionAttributes(getSessionAttributes(request));
		stateBean.setRequestAttributes(getRequestAttributes(request));
		
		request.setAttribute("stateBean", stateBean);
		
		request.getRequestDispatcher("/WEB-INF/state.jsp").forward(request, response);
	}

	public List<AttributeInfo> getApplicationAttributes(HttpServletRequest request){
		List<AttributeInfo> attrs = new LinkedList<AttributeInfo>();
		
		javax.servlet.ServletContext app = request.getServletContext();
		Enumeration<String> names = app.getAttributeNames();
		AttributeInfo ai;
		while(names.hasMoreElements()){
			ai = new AttributeInfo();
			ai.name = names.nextElement();
			ai.value = app.getAttribute(ai.name);
			ai.runtimeType = ai.value.getClass().getCanonicalName();
			attrs.add(ai);
		}
		
		return attrs;
	}
	
	public List<AttributeInfo> getSessionAttributes(HttpServletRequest request){
		List<AttributeInfo> attrs = new LinkedList<AttributeInfo>();
		
		javax.servlet.http.HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		AttributeInfo ai;
		while(names.hasMoreElements()){
			ai = new AttributeInfo();
			ai.name = names.nextElement();
			ai.value = session.getAttribute(ai.name);
			ai.runtimeType = ai.value.getClass().getCanonicalName();
			attrs.add(ai);
		}
		
		return attrs;
	}
	
	public List<AttributeInfo> getRequestAttributes(HttpServletRequest request){
		List<AttributeInfo> attrs = new LinkedList<AttributeInfo>();
		
		Enumeration<String> names = request.getAttributeNames();
		AttributeInfo ai;
		while(names.hasMoreElements()){
			ai = new AttributeInfo();
			ai.name = names.nextElement();
			ai.value = request.getAttribute(ai.name);
			ai.runtimeType = ai.value.getClass().getCanonicalName();
			attrs.add(ai);
		}
		
		return attrs;
	}
	
	public List<Cookie> getCookies(HttpServletRequest request){
		if(request.getCookies()!=null){
			return Arrays.asList(request.getCookies());
		}
		return null;
	}
	
	public List<Header> getHeaders(HttpServletRequest request){
		List<Header> headers = new LinkedList<Header>();
		
		Enumeration<String> names = request.getHeaderNames();
		String name;
		Enumeration<String> headerValues;
		List<String> values;
		Header header;
		String element;
		
		while(names.hasMoreElements()){
			name = names.nextElement();
			values = new LinkedList<String>();
			
			headerValues = request.getHeaders(name);
			while(headerValues.hasMoreElements()){
				element = headerValues.nextElement();
				//System.out.println("adding " + element);
				values.add(element);
			}
			
			//values.add(request.getHeader(name));
			
			header = new Header();
			header.setName(name);
			header.setValues(values);
			headers.add(header);
		}
		
		/*
		for(Header h : headers){
			System.out.println("header = " + h.getName());
		}
		*/
		
		return headers;
	}
	
	public URLParts createURLParts(HttpServletRequest request){
		URLParts u = new URLParts();
		u.setProtocol(request.getProtocol());
		u.setServerName(request.getServerName());
		u.setPort(request.getServerPort());
		u.setServletPath(request.getServletPath());
		u.setQueryString(request.getQueryString());
		return u;
	}
}
