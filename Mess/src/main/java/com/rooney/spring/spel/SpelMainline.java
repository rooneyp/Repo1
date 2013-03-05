package com.rooney.spring.spel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;


public class SpelMainline {
    
	public static void main(String[] args) {
		//mess();

		bootstrap();
		
//    	oldWayWithSysProp();
	}

	protected static void mess() {
		//		System.setProperty("spring.profiles.active","foo");
		//    	ConfigurableApplicationContext ctx = 
		//    			new ClassPathXmlApplicationContext("classpath:/com/rooney/spring/spel/Spel-context-property-source.xml");
		
		       	ConfigurableApplicationContext ctx = new GenericApplicationContext();
		    	MutablePropertySources sources = ctx.getEnvironment().getPropertySources();
		    	sources.addFirst(new MyPropertySource());
		    	
		
				System.out.println(ctx.getBean("stringBean"));
	}
    
	//works
	public static void bootstrap() {
    	ConfigurableApplicationContext ctx = 
    			new ClassPathXmlApplicationContext("classpath:/com/rooney/spring/spel/Spel-bootstrap-context.xml");
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
