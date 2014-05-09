package com.rooney.sts;

/**
 * Reverse a String without any library calls
 * TODO: impl a recurvise method
 */
public class ReverseString {
	public static void main(String[] args) {
		System.out.println(reverseStringWithoutBreak("123456"));
		System.out.println(reverseStringWithoutBreak("12345"));
		System.out.println(reverseStringWithoutBreak("1234"));
        System.out.println(reverseStringWithoutBreak("12")); //1/2 is 0, so loop doesn't kick in
        System.out.println(reverseStringWithoutBreak("1")); //1/2 is 0, so loop doesn't kick in
		System.out.println(reverseStringWithoutBreak(""));
		System.out.println(reverseStringWithoutBreak(null)); //1/2 is 0, so loop doesn't kick in
		
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

        int rhs = 0;
		for(int lhs=0; lhs<input.length() /2; lhs++) {
			rhs = (input.length() -1) - lhs; //adjust by -1 to go from size to index
			if(lhs >= rhs) {
				break;
			}
			output[lhs] = input.charAt(rhs);
			output[rhs] = input.charAt(lhs);
		}
		return String.valueOf(output);
	}


    private static String reverseStringWithoutBreak(String input) {
        if(input == null) {
            return null;
        }

        char[] output = input.toCharArray(); //set input as output so don't have to copy middle digit in odd numbers

        int rhs = 0;
        for(int lhs=0; lhs<input.length() /2; lhs++) { //5:<2 [0,_1_,2,3,4] ; 6:<3 [0,1,_2_,3,4,5]
            rhs = (input.length() -1) - lhs; //adjust by -1 to go from size to index
            output[lhs] = input.charAt(rhs);
            output[rhs] = input.charAt(lhs);
        }
        return String.valueOf(output);
    }
}
