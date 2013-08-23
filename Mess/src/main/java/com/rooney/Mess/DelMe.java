package com.rooney.Mess;


public class DelMe {

    public static void main(String[] args) throws Exception {
    	String before = "ab";
    	char[] afterChars = new char[before.length()];
    	
    	int length = before.length() -1;
    	int i = 0;
    	
    	for(i = 0; length -i >= i; i++) {
    		afterChars[i] = before.charAt(length -i);
    		afterChars[length -i] = before.charAt(i);
    	}
    	
    	System.out.println(afterChars);
    	System.out.println(i);
    }

}
