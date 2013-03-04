package com.rooney.spring.spel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration
public class Spel {
    @Autowired String stringBean;
    
    @Value("${foo}") private String foo;
    
    @Test public void testUppercase() throws Exception {
        assertEquals("FOO", foo);
    }
    
    @Test public void testImportUsingPropertyInAppCtxName() throws Exception {
        assertEquals("hello", stringBean);
    }
    
}
