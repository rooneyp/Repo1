package com.rooney.Mess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DelMe {

    public static void main(String[] args) throws Exception {
        String str = "CREATE SEQUENCE  MYSEQUENCE1  MINVALUE 0 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 0 CACHE 20 ORDER NOCYCLE"
         + "\nCREATE SEQUENCE  MYSEQUENCE2  MINVALUE 0 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 0 CACHE 20 ORDER NOCYCLE";
        System.out.println(str);
        
        String originalToken = "(.+MAXVALUE\\s+)\\d+(.+)ORDER(.+)"; //WORKS
//        String originalToken = "(.+MAXVALUE +)9+(.+)ORDER(.+)"; //WORKS
        String replacementToken = "$1999999999999999999$2$3"; //WORKS
        
//        String replaced = str.replaceAll(Pattern.quote(originalToken), Matcher.quoteReplacement(replacementToken));
        String replaced = str.replaceAll(originalToken, replacementToken); //WORKS
        
        System.out.println(replaced);
        
    }
}
