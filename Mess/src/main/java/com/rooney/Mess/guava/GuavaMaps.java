package com.rooney.Mess.guava;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class GuavaMaps {

	public static void main(String[] args) {
		
		
		Map<Integer, String> lhs = Maps.newLinkedHashMap(ImmutableMap.of(1, "a", 2, "b", 3, "c", 4, "x"        ));
		Map<Integer, String> rhs = Maps.newLinkedHashMap(ImmutableMap.of(        2, "b", 3, "c", 4, "d", 5, "e"));
		
//		useGuavaMaps(lhs, rhs);
		
		useGuavaSets(lhs, rhs);
	}

	private static void useGuavaSets(Map<Integer, String> lhs,
			Map<Integer, String> rhs) {
		SetView<Integer> commonKeys = Sets.intersection(lhs.keySet(), rhs.keySet());
		lhs.keySet().retainAll(commonKeys);
		rhs.keySet().retainAll(commonKeys);
		
		System.out.println("lhs with based common keys" + lhs);
		System.out.println("rhs with based common keys" + rhs);
		System.out.println("lhs values: " + lhs.values()); // iteration order is still upheld
	}

	private static void useGuavaMaps(LinkedHashMap<Integer, String> lhs,
			LinkedHashMap<Integer, String> rhs) {
		MapDifference<Integer, String> difference = Maps.difference(lhs, rhs);
		Map<Integer, String> entriesInCommon = difference.entriesInCommon();
		Map<Integer, ValueDifference<String>> entriesDiffering = difference.entriesDiffering();

		System.out.println(entriesInCommon);
		System.out.println(entriesDiffering);
		
		System.out.println("common keys in maps are" + entriesInCommon.keySet() + ", " +  entriesDiffering.keySet());
		//want to filter each 
		HashSet<Integer> commonKeys = Sets.newHashSet(entriesInCommon.keySet());
		commonKeys.addAll(entriesDiffering.keySet());
		lhs.keySet().retainAll(commonKeys);
		rhs.keySet().retainAll(commonKeys);
		
		System.out.println("lhs with based common keys" + lhs);
		System.out.println("rhs with based common keys" + rhs);
	}
}
