package com.rooney.Mess;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class App {
    public static void main(String[] args) {
    	Parent[] objs = new Parent[] {new Parent(), new Child()};
    	
    	for (Parent parent : objs) {
			process(parent);
		}
    	
    	Parent p = new Parent();
    	Child c = new Child();
    	
    	process(p);
    	process(c); 
    	
    }

    public static void process(Parent p) {
    	System.out.println(" process(Parent p) {");
    	p.foo();
    };
    
    public static void process(Child c) {
    	System.out.println(" process(Child c) {");
    	c.foo();
    };
    
    public static class Parent {
    	public void foo() {
    		System.out.println("Parent.foo");
    	}
    }
    
    public static class Child extends Parent {
    	public void foo() {
    		System.out.println("Child.foo");
    	}
    }
    
    
}
