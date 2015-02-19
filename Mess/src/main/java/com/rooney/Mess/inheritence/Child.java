package com.rooney.Mess.inheritence;

public class Child extends Parent{
	String string = "Child";
	String staticString = "StaticChild";
	
//	public void bar(String msg) {
//		System.out.println("foo called with msg " + msg + ". String is " + getString());
//	}
//	
//	public void staticBar(String msg) {
//		System.out.println("foo called with msg " + msg + ". StaticString is " + getStaticString());
//	}	
	
	
	public String getString() {
		return string;
	}

	public String getStaticString() {
		return staticString;
	}
	
	
}
