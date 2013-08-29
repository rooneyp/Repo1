package com.rooney.euler;

public class Id3LargestPrimeFactor {

	/**
	 * The prime factors of 13195 are 5, 7, 13 and 29.
What is the largest prime factor of the number 600851475143 ?
	 */
	public static void main(String[] args) {
		
		int aNumber = 12;
		
		int[] primes = new int[]{2, 3, 5, 7, 11, 13,17};
		for (int prime : primes) {
			//does it divide evenly
			if(aNumber % prime ==0) {
				System.out.println("div even " + prime);
				if(divideResultIsPrime(aNumber / prime)) {
					System.out.println("div res is prime " + aNumber / prime);
				}
			}
		}
		//do
	}

	private static boolean divideResultIsPrime(int i) {
		// TODO Auto-generated method stub
		return false;
	}

}
