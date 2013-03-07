package com.rooney.spring.spel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;


public class SpelMainline {
	public static void main(String[] args) {
//		mess();

		bootstrap();
		
//    	oldWayWithSysProp();
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
    
	//works
	public static void bootstrap() {
    	ConfigurableApplicationContext ctx = 
    			new ClassPathXmlApplicationContext("classpath:/com/rooney/spring/spel/Spel-bootstrap-context.xml");
		System.out.println(ctx.getBean("stringBean"));
		
//		    @Value("${foo}") private String foo;
//    
//    @Test public void testUppercase() throws Exception {
//        assertEquals("FOO", foo);
//    }
		
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
