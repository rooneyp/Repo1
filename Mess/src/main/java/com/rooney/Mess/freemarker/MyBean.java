package com.rooney.Mess.freemarker;

// class must be public for FM 
public class MyBean {
	String name;

	public MyBean(String name) {
		this.name = name;
	}

	//must have getter for FM
	public String getName() {
		return name;
	}
}