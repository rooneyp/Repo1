package com.rooney.Mess.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordering {
	public static void main(String[] args) {
		List<Foo> foos = buildFoos(3);
		
		Collections.shuffle(foos);
		sortManually(foos);
		System.out.println(foos);
		
		Collections.shuffle(foos);
		sortCompartorChain(foos);
		System.out.println(foos);
	}

	private static void sortCompartorChain(List<Foo> foos) {
		
	}

	private static void sortManually(List<Foo> foos) {
		Collections.sort(foos, new Comparator<Foo>() {
			public int compare(Foo o1, Foo o2) {
				int nameCompare = o1.name.compareTo(o2.name);
				if(nameCompare != 0) {
					return nameCompare;
				} else {
					return o1.age.compareTo(o2.age);
				}
			}
		});
	}

	private static List<Foo> buildFoos(int i) {
		List<Foo> foos = new ArrayList<Foo>();
		
		for(char c = 'A'; c <= 'A'+i; c++) {
			for (int j = 1; j <= i; j++) {
				foos.add(new Foo(""+c, j));
			}
		}
		
		return foos;
	}

	public static class Foo {
		public Foo(String name, Integer age) {
			super();
			this.name = name;
			this.age = age;
		}
		String name; 
		Integer age;
		@Override
		public String toString() {
			return name + age;
		}
		
		
	}
	
}
