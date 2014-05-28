package com.rooney.java8.jaxenter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//http://jaxenter.com/lambdas-in-java-8-part-1-49700.html
// for keplar sr2 - jdk8 patch update - http://download.eclipse.org/eclipse/updates/4.3-P-builds/
// for luna - wait for 'Eclipse IDE for Java Developers' m7 - https://www.eclipse.org/downloads/index-developer.php
//          - or get 'eclipse standard' integration build -  http://download.eclipse.org/eclipse/downloads/
public class Jaxenter {
    static List<Person> people = Arrays.asList(
            new Person("Ted", "Neward", 42),
            new Person("Charlotte", "Neward", 39),
            new Person("Michael", "Neward", 19),
            new Person("Matthew", "Neward", 13),
            new Person("Neal", "Ford", 45),
            new Person("Candy", "Ford", 39),
            new Person("Jeff", "Brown", 43),
            new Person("Betsy", "Brown", 39)
    );

    public static void main(String... args) {
//	    listing3();
//		listing4();
        listing26();
	  }

	private static void listing4() {
		Comparator<String> c = (lhs, rhs) -> lhs.compareTo(rhs); //params on left. type is optional where it can be figured out
		int result = c.compare("Hello", "World");
		System.out.println("result is " + result);

		List<String> strings = Arrays.asList("A", "C", "B") ;
		strings.sort((lhs, rhs) -> lhs.compareTo(rhs)); //minimal impl for functional interface
		System.out.println(strings);

		strings.sort((lhs, rhs) -> {
			System.out.println("comparing");
			return lhs.compareTo(rhs);
		}); //extra logic impl for functional interface
		System.out.println(strings);

	}

	private static void listing3() {
		//auto conf form closure to functional i/f
		Runnable r2 = () -> System.out.println("Howdy, world!");
	    r2.run();

	    ((Runnable)() -> System.out.println("Howdy, world! 2") ).run();

	}

    private static void listing26() {
        people.parallelStream()
                .filter((it) -> it.getAge() >= 21)
                .forEach((it) -> System.out.println("Have a beer " + it.getFirstName() + Thread.currentThread()));
    }


    public static class Person {
        private final String firstName;
        private final String lastName;
        private final int age;

        public Person(String fn, String ln, int a) {
            this.firstName = fn; this.lastName = ln; this.age = a;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public int getAge() { return age; }
    }

}
