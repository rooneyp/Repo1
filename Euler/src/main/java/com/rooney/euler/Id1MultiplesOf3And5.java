package com.rooney.euler;

/**
 * Problem:
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
 * Find the sum of all the multiples of 3 or 5 below 1000.
 * @author prooney
 *
 */
public class Id1MultiplesOf3And5 {
	
	public long calcResult(int target, int... multiples) {
		long result = 0;
		
		for(int i = 1; i < target; i++) { // ignore 0
			result += returnMultiple(i, multiples);
		}
		return result;
	}

	private long returnMultiple(int aNumber, int... multiples) {
		long result = 0;
		
		for (int multiple : multiples) {
			if(aNumber % multiple == 0 ) {
				result+= aNumber;
				break; //exclude dups
			}
		}
		return result;
	}

	
	public long calcResultHardcodedMultiples(int target) {
		long result = 0;
		for(int i = 1; i < target; i++) { // ignore 0
			if(i % 3 == 0 || i % 5 == 0) {
				result+= i;
			}
		}
		return result;		
	}
	
	
	//ans is 233168
	public static void main(String[] args) {
		System.out.println("result: " + new Id1MultiplesOf3And5().calcResult(1000, 3,5));
		System.out.println("result: " + new Id1MultiplesOf3And5().calcResultHardcodedMultiples(1000));
	}
}
