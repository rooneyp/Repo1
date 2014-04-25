package com.rooney.sts;

/**
 * Reverse a String without any library calls
 *
 */
public class ReverseString {
	public static void main(String[] args) {
		System.out.println(reverseString("123456"));
		System.out.println(reverseString("12345"));
		System.out.println(reverseString("1234"));
		System.out.println(reverseString("1")); //1/2 is 0, so loop doesn't kick in
		System.out.println(reverseString(null)); //1/2 is 0, so loop doesn't kick in
		
		System.out.println(new StringBuilder("Foo").reverse());
	}

	/**
	 * Reverses characters in a String.
	 * Cost is String length /2.
	 * @param input
	 * @return
	 */
	private static String reverseString(String input) {
		if(input == null) {
			return null;
		}
		
		char[] output = input.toCharArray(); //set input as output so don't have to copy middle digit in odd numbers
		
		for(int lhs=0; lhs<input.length() /2; lhs++) {
			int rhs = (input.length() -1) - lhs; //adjust by -1 to go from size to index
			if(lhs >= rhs) {
				break;
			}
			output[lhs] = input.charAt(rhs);
			output[rhs] = input.charAt(lhs);
		}
		return String.valueOf(output);
	}
	
	
	
}
