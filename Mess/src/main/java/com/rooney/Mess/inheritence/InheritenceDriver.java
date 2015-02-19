package com.rooney.Mess.inheritence;

public class InheritenceDriver {

	public static void main(String[] args) {
		new Parent().foo("parent");
		new Parent().staticFoo("parent");
		new Child().foo("child");
		new Child().staticFoo("child");
		
		new Parent().bar("parent");
		new Parent().staticBar("parent");
		new Child().bar("child");
		new Child().staticBar("child");
		
	}

}
