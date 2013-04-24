package com.rooney.spring.spel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:/com/rooney/spring/spel/Spel-bootstrap-context.xml" })
public class SpelMainline {
    @Autowired ApplicationContext appCtx;
    
    @Test
    public void test() { //NOT WOrking as GenericApplicationContext does not support multiple refresh attempts
        assertEquals("hello from Spel-context3.xml", appCtx.getBean("stringBean"));
    }
    
	public static void main(String[] args) {
		bootstrap();
	}
	
	//works sort of. doesn't resolve placeholders in the loaded context. can;t load beans from bootstrap context
	public static void bootstrap() {
//	    System.setProperty("context", "Spel-context2.xml");
	    ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/com/rooney/spring/spel/Spel-bootstrap-context.xml");
	    System.out.println(ctx.getBean("stringBean"));
	    System.out.println(ctx.getBean("stringBeanBootstrap"));
	}

	
	protected static void mess() {
		//		System.setProperty("spring.profiles.active","foo");
//		    	ConfigurableApplicationContext ctx = 
//		    			new ClassPathXmlApplicationContext("classpath:/com/rooney/spring/spel/Spel-context.xml");
    
	    //can we add appcontext.xml to a genericAppCtx and then add property sources, when call refresh?
	    ConfigurableApplicationContext ctx = new GenericApplicationContext();
	    ConfigurableEnvironment environment = ctx.getEnvironment();

	    
        MutablePropertySources sources = environment.getPropertySources();
	    sources.addFirst(new MyPropertySource());

		System.out.println(ctx.getBean("stringBean"));
	}
    
	
	public static class MyPropertySource extends PropertySource {
		
		public MyPropertySource() {
			super("MyPropertySource");
		}
		
		@Override
		public Object getProperty(String name) {
			return null;
		}
		
	}
    

	protected static void oldWayWithSysProp() {
		System.setProperty("context2","Spel-context2.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/com/rooney/spring/spel/Spel-context.xml");
		
		System.out.println(ctx.getBean("stringBean"));
	}
    
}
