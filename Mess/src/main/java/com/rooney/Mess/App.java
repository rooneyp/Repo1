package com.rooney.Mess;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class App {
    public static void main(String[] args) {
    	String s = "abcdef";
    	
    	char[] charArray = s.toCharArray();
    	char[] charArrayOut = new char[charArray.length];
    	
    	int lastIndex = charArray.length -1;

//    	for(int i=0; i< charArray.length / 2 + charArray.length % 2; i++) {
//    		charArrayOut[i] = charArray[arrayLen -i];
//    		charArrayOut[arrayLen -i] = charArray[i];
//    		System.out.println("loop");
//    	}
    	
    	
    	int i = 0;
    	while(i <= (lastIndex -i)) {
    		charArrayOut[i] = charArray[lastIndex -i];
    		charArrayOut[lastIndex -i] = charArray[i];
    		System.out.println("loop");
    	}

    	System.out.println(charArrayOut);
    	
    }

}
