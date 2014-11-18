package com.rooney.Mess.guava;

import com.google.common.collect.ArrayListMultimap;

public class GuavaCollection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		arrayListMultiMap();
		
		fluent();
	}

	private static void fluent() {
//		List<State> mdwStates = FluentIterable
//		.from(states)
//		.filter(State.byMdwRegion)
//		.filter(byPopulationGT?)
//		.filter(whereStateStartsWithC)
//		.toList();
		
	}

	private static void arrayListMultiMap() {
		ArrayListMultimap<String, Integer> storageByType = ArrayListMultimap.create();
		
		storageByType.put("A", 1);
		storageByType.put("A", 2);
		storageByType.put("B", 3);
		storageByType.put("B", 4);
		
		System.out.println(storageByType);
	}

}
