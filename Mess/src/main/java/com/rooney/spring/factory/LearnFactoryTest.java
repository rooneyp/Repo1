package com.rooney.spring.factory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration //defaults to "classpath:/com/rooney/spring/factory/LearnFactoryTest-context.xml"
public class LearnFactoryTest {
	@Autowired String testBean;
	@Autowired ApplicationContext appContext;
	
	@Test public void test2() {
//		System.out.println(appContext.getBean("realFooFactory"));
//		System.out.println(appContext.getBean("realFooFactory", "XXX", "999"));
		//System.out.println(appContext.getBean("realFooFactory", "101")); NPE as it prob can't fund ctor
		
		System.out.println(appContext.getBean("childFooFactory", "XXX", "999"));
		System.out.println(appContext.getBean("childFooFactory", "999"));
		
	}
	
	
//	@Test public void test1() {
//		assertThat(testBean, is("testBean"));
//	}
}
