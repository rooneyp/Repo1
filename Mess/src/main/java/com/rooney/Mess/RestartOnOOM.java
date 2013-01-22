package com.rooney.Mess;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * run with .bat/.sh<br>
 * java -Xmx10m -XX:OnOutOfMemoryError=go.bat -jar go.jar 10000 10000
 * 
 * @author prooney
 *
 */
public class RestartOnOOM {
    private static int NUM_DATA = 10000;
    private static int DATA_LENGTH = 1000;
    
    public static void main(String[] args) {
        if(args.length > 0) {
            NUM_DATA = Integer.valueOf(args[0]);
        } 
        if(args.length > 1) {
            DATA_LENGTH = Integer.valueOf(args[1]);
        }
            
        System.out.printf("Starting with NUM_DATA %s DATA_LENGTH %s \n", NUM_DATA, DATA_LENGTH);
        
        List<String> strings = new ArrayList<String>();
        for(int i=0;i< NUM_DATA; i++ ) {
            strings.add(RandomStringUtils.randomAlphabetic(DATA_LENGTH));
        }
        
        System.out.println("Ending");
    }
}
