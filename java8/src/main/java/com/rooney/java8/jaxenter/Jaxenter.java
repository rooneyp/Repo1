package com.rooney.java8.jaxenter;

public class Jaxenter {

	public static void main(String... args) {
	    listing3();
	  }

	private static void listing3() {
		Runnable r2 = () -> System.out.println("Howdy, world!");
	    r2.run();
	    
	    ((Runnable)() -> System.out.println("Howdy, world! 2") ).run();
	    
	} 

}
