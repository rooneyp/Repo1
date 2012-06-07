package com.rooney.predicates.GPredicates;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
/**
 * Predicates.in?
 * Predicate.apply
 * 
 */
public class App {

	public static class User {
		long id; 
		String firstName;
		String lastName;
		String login;
		
		public User(long id, String firstName, String lastName, String login) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.login = login;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", firstName=" + firstName
					+ ", lastName=" + lastName + ", login=" + login + "]";
		}
		
	}

	private List<User> 	users = Arrays.asList(new User(1L, "sylwek", "stall", "rambo"),
			  new User(2L, "arnold", "schwartz", "commando"),
			  new User(3L, "paul", "rooney", "dave12"),
			  new User(4L, "olivia", "rooney", "shelbourne33")
	);

	public static void main(String[] args) {
		new App().useRemoveOneListFromAnother(); //useMethodToBuildAnonPredicate(); //useIn();
	}
	
	void splitting() {
		//split string and get iterator
		String pattern = "Foo,Bar,Boo";
		Iterable pattLst = Splitter.on(",").split(pattern);
	}
	
	void useMethodToBuildAnonPredicate() {
		System.out.println(Collections2.filter(users, methodToBuildAnonPredicate(5)));
	}
	
	
	//Interesting: a way to create reusable predicates without needing class per pred.
	// and instance variables. This could live in a Factory class.
	// are the hard to find in eclipse though?
	Predicate<User> methodToBuildAnonPredicate(final int nameLen) {
		return new Predicate<User>() {
			public boolean apply(User input) {
				return input.firstName.length() <= nameLen;
			}
		};
	}
	
	
	void useRemoveOneListFromAnother() {
		List<User> excludeRooneys= Arrays.asList(users.get(2), users.get(3));
		
		//3 lists. input, goodList, badList. Entry from input is in goodlist but not in bad list
		Collection<User> filteredList = Collections2.filter(
				users, 
				and(in(users), not(in(excludeRooneys)))
				);
		System.out.println(filteredList);
		
		//2 lists, input, badList. remove badlist entries from input
		System.out.println(
			Collections2.filter(users, not(in(excludeRooneys)))
				);
	}
	
	void useIn() {
		Predicate<User> inPredicate =in(users);
		System.out.println("is paul is list? : " + inPredicate.apply(users.get(2)));
		//.apply(new ShouldNotHaveDigitsInLoginPredicate());
		
		//Returns the elements that satisfy a predicate. like select.
		Collection<User> usersWithoutDigitsInLogin = Collections2.filter(users, new ShouldNotHaveDigitsInLoginPredicate());
		System.out.println("Filter/select users withoutDigitsInLogin: " + usersWithoutDigitsInLogin);
		
	}
	
	public class ShouldNotHaveDigitsInLoginPredicate implements Predicate<User> {	
	    public boolean apply(User user) {
	        checkNotNull(user);
	        return CharMatcher.DIGIT.retainFrom(user.login).length() == 0;
	    }    
	} 	
	
}
