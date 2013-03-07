package com.rooney.spring.spel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration( locations = { "classpath:/com/rooney/spring/spel/Spel-bootstrap-context.xml" }, loader = SpelContextLoader.class)
//TODO http://blog.springsource.com/2011/06/21/spring-3-1-m2-testing-with-configuration-classes-and-profiles/
//use classes or @ContextConfiguration(loader=AnnotationConfigContextLoader.class) and a class annotated with @Configuration

public class Spel {
    @Autowired String stringBean;
    
    @Value("${foo}") private String foo;
    
    @Test public void testUppercase() throws Exception {
        assertEquals("FOO", foo);
    }
    
    @Test public void testImportUsingPropertyInAppCtxName() throws Exception {
        assertEquals("hello from Spel-context3.xml", stringBean);
    }
    
}
