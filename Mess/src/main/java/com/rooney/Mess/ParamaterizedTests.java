package com.rooney.Mess;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ParamaterizedTests {

     private int number;
 
     public ParamaterizedTests(int number) {
        this.number = number;
     }
 
     @Parameters
     public static Collection<Object[]> data() {
       System.out.println("New Object");
       Object[][] data = new Object[][] { { 1 }, { 2 }, { 3 }, { 4 } };
       return Arrays.asList(data);
     }
 
     @Test
     public void pushTest() {
       System.out.println("Parameterized Number is : " + number);
     }

}
