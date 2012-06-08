package com.rooney.Mess;

public class DelMe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DelMe().go();
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
