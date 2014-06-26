package com.rooney.spring.factory;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class FooFactory {
	public FooFactory() {
//		System.out.println("FooFactory Constructed " + System.identityHashCode(this));
	}

	//MUST be non static for prototype
	public String newFoo(String arg1, String arg2) {
//		System.out.println("FooFactory.newFoo ");
		return arg1 + "_" + arg2;
	}
	
	//static for Singleton
	public static String newSingletonFoo(String arg1, String arg2) {
//		System.out.println("FooFactory.newSingletonFoo");
		return arg1 + "_" + arg2;
	}
	
	
	//TODO add autowiring for params
	//MUST use static, which is weird as when using xml config, the method must not be static!! 
	@Bean
	@Scope("prototype")
	public static String newAnnotatedFoo(String arg1, String arg2) {
//		System.out.println("FooFactory.newAnnotatedFoo ");
		return arg1 + "_" + arg2 + UUID.randomUUID();
	}
	
	@Bean
	public static String newAnnotatedFooNoParamsStatic() {
//		System.out.println("FooFactory.newAnnotatedFooNoParams ");
		return "NO_PARAMS" + UUID.randomUUID();
	}

	//all params are autowired, with optional explicit qualifier
	//'autowired' not required but if used gives a warning but still works "Autowired annotation is not supported on static methods"
	@Bean
	@Scope("prototype")
	public static String newAnnotatedFooWithFullAutowire(@Qualifier("testBean") String arg1, @Qualifier("testBean") String arg2) {
//		System.out.println("FooFactory.newAnnotatedFooWithFullAutowire ");
		return arg1 + "_" + arg2 + UUID.randomUUID();
	}
	
	
	//autowiring not specified, but we get it for free
	@Bean
	@Scope("prototype")
	public static String newAnnotatedFooRelyingOnUniqueAutowiredBeans(Integer arg1, Double arg2) {
//		System.out.println("FooFactory.newAnnotatedFoo ");
		return "" + arg1 + "_" + arg2 + UUID.randomUUID();
	}	

}
