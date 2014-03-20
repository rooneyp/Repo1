package com.rooney.java8.jaxenter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Jaxenter {

	public static void main(String... args) {
	    listing3();

		listing4();
	  }

	private static void listing4() {
		Comparator<String> c = (String lhs, String rhs) -> lhs.compareTo(rhs);
		int result = c.compare("Hello", "World");
		System.out.println("result is " + result);
		
		List<String> strings = Arrays.asList("A", "C", "B") ;
		strings.sort((lhs, rhs) -> lhs.compareTo(rhs)); //minimal impl for finctional interface
		System.out.println(strings);

		strings.sort((lhs, rhs) -> {
			System.out.println("comparing"); 
			return lhs.compareTo(rhs);
		}); //extra logic impl for finctional interface
		System.out.println(strings);
		
	}

	private static void listing3() {
		Runnable r2 = () -> System.out.println("Howdy, world!");
	    r2.run();
	    
	    ((Runnable)() -> System.out.println("Howdy, world! 2") ).run();
	    
	} 

}
