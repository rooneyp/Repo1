package com.rooney.spring.factory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration //defaults to "classpath:/com/rooney/spring/factory/LearnFactoryTest-context.xml"
public class LearnFactoryTest {
	@Autowired @Qualifier("testBean") String testBean;
	@Autowired ApplicationContext ctx;
	
	@Test public void test2() {
		assertThat(testBean, is("poppop"));
		
		assertEquals("abstractFooProtoType_ARG1_childFooPrototype_ARG2", ctx.getBean("childFooPrototype"));
		assertNotSame(ctx.getBean("childFooPrototype"), ctx.getBean("childFooPrototype")); //"expecting diff instances"
		
		assertEquals("abstractFooProtoType_ARG1_childFooSingleton_ARG2", ctx.getBean("childFooSingleton"));
		assertSame(ctx.getBean("childFooSingleton"), ctx.getBean("childFooSingleton")); //"expecting same instance"
		
		assertEquals("A_B", ctx.getBean("childFooPrototype", "A", "B")); //able to override all ctor params"
//
//		//can we just supply a ctor param to the child and take advantage of the parents default params = NO!!!
//		
		boolean caughtEx = false;
		try {
			System.out.println(ctx.getBean("childFooPrototype", "101")); //NPE as it prob can't fund ctor
		} catch (Exception e){ caughtEx = true; }
		assertTrue(caughtEx); //
		
		caughtEx = false;
		try {
			System.out.println(ctx.getBean("childFooSingleton", "101", "102")); 
		} catch (Exception e){ caughtEx = true; }
		assertTrue(caughtEx); //org.springframework.beans.factory.BeanDefinitionStoreException: Can only specify arguments for the getBean method when referring to a prototype bean definition

		assertTrue(((String)ctx.getBean("newAnnotatedFooNoParamsStatic")).startsWith("NO_PARAMS"));
		assertEquals(ctx.getBean("newAnnotatedFooNoParamsStatic"), ctx.getBean("newAnnotatedFooNoParamsStatic"));

		assertTrue(((String)ctx.getBean("newAnnotatedFoo", "aaa", "bbb")).startsWith("aaa_bbb"));
		assertNotSame(ctx.getBean("newAnnotatedFoo", "aaa", "bbb"), ctx.getBean("newAnnotatedFoo", "aaa", "bbb"));
		
		assertTrue(((String)ctx.getBean("newAnnotatedFooWithFullAutowire")).startsWith("poppop_poppop"));
		
		//we get autowiring for free for @Bean methods
		assertTrue(((String)ctx.getBean("newAnnotatedFooRelyingOnUniqueAutowiredBeans")).startsWith("100_100.001"));

		System.out.println(ctx.getBean("newAnnotatedFooRelyingOnUniqueAutowiredBeans", 111, 111.111D));
		
		//CONCLUSION: it seems impossible to have a factory method take some autowired params and some dynamic
	}
	
	
//	@Test public void test1() {
//		
//	}
}
