package com.rooney.sts;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Task: Find the missing term in an Arithmetic Progression.
	 
	An Arithmetic Progression is defined as one in which there is a constant difference between the consecutive terms of a given series of numbers. You are provided with consecutive elements of an Arithmetic Progression. There is however one hitch: Exactly one term from the original series is missing from the set of numbers which have been given to you. The rest of the given series is the same as the original AP.  Find the missing term.  
	 
	 
	Input Format
	The first line contains an Integer N, which is the number of terms which will be provided as input.
	This is followed by N consecutive Integers, with a space between each pair of integers. All of these are on one line, and they are in AP (other than the point where an integer is missing).
	 
	 
	Output Format
	One Number which is the missing integer from the series.
	 
	Sample Input
	5
	1 3 5 9 11  
	 
	Sample Output
	7
	 
	Explanation
	You are provided with 5 integers. As you can can observe, they have been picked from a series, in which the starting term is 1 and the common difference is 2. The only abberration, i.e. the missing term (7), occurs between 5 and 9. This is the missing element which you need to find.
	 
	Constraints
	3 <= N <= 2500
	All integers in the series will lie in the range [-10^6,+10^6].
 * @author paul
 *
 */
public class Solution {
    public static void main(String args[] ) throws Exception {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        
        String numberOfTermsString = inputReader.readLine();
        String termsString = inputReader.readLine();
        
        
        findMissingTerm(numberOfTermsString, termsString);
    }

	private static void findMissingTerm(String numberOfTermsString,	String termsString) {
		int numOfTerms = Integer.valueOf(numberOfTermsString);
        String[] splitTermsString = termsString.split(" ", numOfTerms);
        int[] splitTerms = new int[splitTermsString.length];
        for (int i = 0; i < splitTermsString.length; i++) {
			splitTerms[i] = Integer.valueOf(splitTermsString[i]);
		}
        
        int diff = splitTerms[1] - splitTerms[0];
        for (int i = 2; i < splitTerms.length; i++) {
        	if(splitTerms[i] - splitTerms[i-1] != diff) {
        		System.out.println();
        	}
        }
        
        
	}
    
    
}
