package com.rooney.Mess;

import junit.framework.Assert;

import org.junit.Test;

public class BooleanLogic {
    private int foo;
    private boolean hasFoo;
    private int bar;
    private boolean hasBar;


    @Test
    public void testEqualsMethod() throws Exception {
        foo = 100;
        hasFoo = true;
        bar = 100;
        hasBar = true;
        
        myEquals("Test both has's = true");
        
        foo = 101;
        myEquals("Test both has's = true, but values diff");
        
        hasFoo = false;
        myEquals("Test one has = false");
        
        hasBar = false;
        myEquals("Test has's = false");
        
    }


    public void myEquals(String string) {
        System.out.println(string);
        if(hasFoo ^ hasBar) {
            System.out.println("mixed");
            return;
        }
            
        if(hasFoo && hasBar) {
            System.out.println("both has's true, result of values eq check is: " + (foo == bar));
            return;
        }
        
        
        System.out.println("Ignored as the has's must be false");
    }
    
    
    @Test
    public void testNotEqualsOnMissingOptionalField() {
        boolean foo = false; //optional
        boolean hasFoo = false;
        int bar = 3; //optional and has a random value
        boolean hasBar = false;
        int result = 0;
        
        Assert.assertFalse(hasFoo);
        Assert.assertFalse(hasBar);
        
        //test optional not set
        foo = true; 
        hasFoo = true;
        
        
        if( (hasFoo && foo == true)
            && !(hasBar && bar == 3) //if hasBar not present any equality eval fails. (bar == 3) is false if bar doesn't exist    
          ) {
            result = 3;
        } else {
            result = 99;
        }
        
        Assert.assertEquals(3, result);
    }
}
