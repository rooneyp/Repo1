package com.rooney.Mess;

import java.util.Arrays;
import java.util.List;

public class DelMe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

        List<String> strings = Arrays.asList("a", "b");
        System.out.println(strings);
	}

	private void go() {
		int x = 100;
		
		foo(100);
		
		System.out.println("go " + x);
		
	}

	private void foo(int i) {
		i++;
		System.out.println("foo " + i);
		
	}

}
