package com.xzymon.servlet.util;

import java.util.LinkedList;
import java.util.List;

public class Header {
	private String name;
	private List<String> values;
	
	public Header(){
		values = new LinkedList<String>();
	}
	
	public String getName() {
		System.out.println("getting name: " + name);
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getValues() {
		System.out.println("getting values of name" + name);
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	
}
