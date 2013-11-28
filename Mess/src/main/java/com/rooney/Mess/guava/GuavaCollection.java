package com.rooney.Mess.guava;

import com.google.common.collect.ArrayListMultimap;

public class GuavaCollection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		arrayListMultiMap();
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
