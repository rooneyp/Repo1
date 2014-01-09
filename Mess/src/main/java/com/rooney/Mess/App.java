package com.rooney.Mess;

import java.io.Serializable;

/**
 * Hello world!
 * 
 */
public class App implements Serializable{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		StringBuffer buff = new StringBuffer();
    	for(int i = 22000; i <=22059; i++) {
    		buff.append(i).append(" ");
    	}
    	
    	System.out.println(buff);
    }

}
