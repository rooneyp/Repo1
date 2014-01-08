package com.rooney.Mess;

public class Cloning {

	public static void main(String[] args) throws Exception {
		Foo f1 = new FooImpl(100, "100");

		Foo f2 = (Foo) f1.clone();
		
		
	}
	
	interface Foo extends Cloneable {
		int getInt();
		String getString();
		
		Object clone() throws CloneNotSupportedException; //have to add method
	}
	
	public static class FooImpl implements Foo {
		private int myInt;
		private String myString;
		
		
		public FooImpl(int myInt, String myString) {
			this.myInt = myInt;
			this.myString = myString;
		}

		public int getInt() {
			return myInt;
		}

		public String getString() {
			return myString;
		}

		@Override
		public Object clone() throws CloneNotSupportedException { //have to make public
			return super.clone();
		}
		
		
	}
}
