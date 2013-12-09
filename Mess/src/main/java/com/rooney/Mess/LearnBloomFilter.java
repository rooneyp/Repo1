package com.rooney.Mess;

import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class LearnBloomFilter {

	public static void main(String[] args) {
		Person dave1 = new Person(1, "dave1", "theRave1", 1971);
		Person dave2 = new Person(2, "dave2", "theRave2", 1972);
		Person dave3 = new Person(3, "dave3", "theRave3", 1973);
		Person dave4 = new Person(4, "dave4", "theRave4", 1974);
			
		List<Person> friendsList = Lists.newArrayList(dave1, dave2, dave3);
		
		BloomFilter<Person> friends = BloomFilter.create(createFunnel(), 500, 0.01);
		for(Person friend : friendsList) {
		  friends.put(friend);
		}		
	
		System.out.println(friends.mightContain(dave1));
		System.out.println(friends.mightContain(dave4));
		
	}

	private static Funnel<Person> createFunnel() {
		return new Funnel<Person>() {
			  public void funnel(Person person, PrimitiveSink into) {
			    into
			      .putInt(person.id)
			      .putString(person.firstName, Charsets.UTF_8)
			      .putString(person.lastName, Charsets.UTF_8)
			      .putInt(person.birthYear);
			  }
			};
	}
	
	public static class Person {
		public Person(int id, String firstName, String lastName, int birthYear) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.birthYear = birthYear;
		}
		int id;
		String firstName;
		String lastName;
		int birthYear;
	}	
}
