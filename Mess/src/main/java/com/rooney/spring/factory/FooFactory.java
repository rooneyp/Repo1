package com.rooney.spring.factory;

import org.springframework.stereotype.Component;

@Component
public class FooFactory {

	//non static
	public String newFoo(String arg1, String arg2) {
		return arg1 + "_" + arg2;
	}
}
