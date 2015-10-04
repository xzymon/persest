package com.xzymon.servlet.util;

public class AttributeInfo {
	public String name;
	public Object value;
	public String runtimeType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public String getValueAsStringWithMaxLengthFiltering(int maxLength) {
		String str = value.toString();
		if(str.length()>maxLength){
			str = "[too long]";
		}
		return str;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getRuntimeType() {
		return runtimeType;
	}
	public void setRuntimeType(String runtimeType) {
		this.runtimeType = runtimeType;
	}
	
	
}
