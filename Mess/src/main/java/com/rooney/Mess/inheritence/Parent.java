package com.rooney.Mess.inheritence;

public class Parent {
	String string = "Parent";
	String staticString = "StaticParent";
	
	public void foo(String msg) {
		System.out.println("foo called with msg " + msg + ". String is " + string);
	}
	
	public void staticFoo(String msg) {
		System.out.println("foo called with msg " + msg + ". StaticString is " + string);
	}
	
	public void bar(String msg) {
		System.out.println("bar called with msg " + msg + ". String is " + getString());
	}
	
	public void staticBar(String msg) {
		System.out.println("bar called with msg " + msg + ". StaticString is " + getStaticString());
	}
	
	
	public String getString() {
		return string;
	}

	public String getStaticString() {
		return staticString;
	}
	
}
