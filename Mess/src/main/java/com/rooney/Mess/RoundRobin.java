package com.rooney.Mess;

import java.util.Arrays;
import java.util.List;

public class RoundRobin {
	public static void main(String[] args) {
		roundRobin();
	}

	private static void roundRobin() {
		List<String> nodes = Arrays.asList("A", "B", "C");
		int index = 0;
		for(int i = 0; i< 100; i ++) {
			System.out.println(nodes.get((index++) % nodes.size()));	
		}
	}
}
