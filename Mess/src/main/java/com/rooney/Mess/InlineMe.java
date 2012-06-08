package com.rooney.Mess;

public class InlineMe {

	int counter = 0;

	public static void main(String args[]) {
		InlineMe im = new InlineMe();
//		im.method1();
	}
	
	
	public void method2() {
		
	}

	
	
	
	public void method1() {
		for (int i = 0; i < 1000; i++) {
			addCount();
		}
		System.out.println("counter=" + counter);
	}

	public int addCount() {
		counter = counter + 1;
		return counter;
	}

}