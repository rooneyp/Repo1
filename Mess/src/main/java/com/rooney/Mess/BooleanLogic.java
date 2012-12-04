package com.rooney.Mess;

import junit.framework.Assert;

import org.junit.Test;

public class BooleanLogic {
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
