package com.rooney.Mess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.io.Files;
import com.rooney.Mess.codegen.Foo;

public class DelMe {

    public static void main(String[] args) throws Exception {
    	
    }

    public static class Parent {
    	protected static boolean foo = true;

		public boolean isFoo() {
			return foo;
		}

		public void setFoo(boolean foo) {
			Parent.foo = foo;
		}
    	
    }
    
    public static class child extends Parent{
    	public void go() {
    		System.out.println(isFoo() + "" +  foo);
    	}
    }
}
