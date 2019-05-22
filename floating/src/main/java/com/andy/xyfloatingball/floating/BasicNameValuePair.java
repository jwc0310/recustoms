package com.andy.xyfloatingball.floating;

public class BasicNameValuePair implements NameValuePair{
	private String name;
	private String value;
	
	
	public BasicNameValuePair(String name, String value){
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

}
