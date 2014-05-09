package com.rooney.sts;

/**
 * "Write a program that prints the numbers from 1 to 100. 
 *  But for multiples of three print “Fizz” instead of the number and for the multiples of five print “Buzz”.
 *  For numbers which are multiples of both three and five print “FizzBuzz”."
 * @author paul
 *
 */
public class FizzBuzz {
	public static void main(String[] args) {
//		printFizzBuzzOutputUsingIncrementalOutput();
//		printFizzBuzzOutputUsingIfElse();
//		printFizzBuzzOutputUsingStringBuilder();
		printFizzBuzzOutputUsingIfElseWith3Mods();
	}

	private static void printFizzBuzzOutputUsingIncrementalOutput() {
		boolean matched = false;
		for(int i = 1; i<=100; i++) {
			matched = false;
			
			if(i % 3 == 0) {
				System.out.print("Fizz");
				matched = true;
			} 
			if(i % 5 == 0) {
				System.out.print("Buzz");
				matched = true;
			}
			if(! matched) {
				System.out.print(i);
			}
			
			System.out.println();
		}
	}	
	
	//nice, but 2 mods and an equality check per iteration
	private static void printFizzBuzzOutputUsingStringBuilder() {
		StringBuilder output = new StringBuilder();
		
		for(int i = 1; i<=100; i++) {
			output.setLength(0);
			if(i % 3 == 0) {
				output.append("Fizz");
			} 
			if(i % 5 == 0) {
				output.append("Buzz");
			}
			if(output.length() == 0) {
				output.append(i);
			}
			System.out.println(output);
		}
	}
	
	private static void printFizzBuzzOutputUsingIfElse() {
		boolean isMultipleOf3;
		boolean isMultipleOf5;
		
		for(int i = 1; i<=100; i++) {
			isMultipleOf3 = i % 3 == 0;
			isMultipleOf5 = i % 5 == 0;
			
			if(isMultipleOf3 && isMultipleOf5) {
				System.out.println("FizzBuzz");
			} else if(isMultipleOf3) {
				System.out.println("Fizz");
			} else if(isMultipleOf5) {
				System.out.println("Buzz");
			} else {
				System.out.println(i);
			}
		}
	}
	
	//easiest to read, less code, up to three Mods happening per iteration
	//Fizz 27, Buzz 14, FizzBuzz 6
	private static void printFizzBuzzOutputUsingIfElseWith3Mods() {
		for(int i = 1; i<=100; i++) {
			if(i % 15 == 0) {
				System.out.println("FizzBuzz");
			} else if(i % 3 == 0) {
				System.out.println("Fizz");
			} else if(i % 5 == 0) {
				System.out.println("Buzz");
			} else {
				System.out.println(i);
			}
		}
	}
	

}
