package com.rooney.Mess.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Iterators {

	public interface DataGen {
		List<String> gen();
	}

	private static final int DATA_SIZE = 100000;
	private static final int EXECUTIONS = 10;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		DataGen arrayList = new DataGen() {
			public List<String> gen() {
				return new ArrayList<String>(DATA_SIZE);
			}
		};
		
		DataGen linkedList = new DataGen() {
			public List<String> gen() {
				return new LinkedList<String>();
			}
		};
		
		long totalTime = runTest(arrayList);
		System.out.println("ArrayList avg " + totalTime / EXECUTIONS);
		
		totalTime = runTest(linkedList);
		System.out.println("LinkedList avg " + totalTime / EXECUTIONS);
		
//		Thread.sleep(15000);
	}

	private static long runTest(DataGen al) {
		long totalTime = 0;
		long time = 0;
		
//		Thread.sleep(5000);
		
		for (int i = 0; i < EXECUTIONS; i++) {
			time = arrayListIterator(al);
			System.out.println(time);
			totalTime += time;
		}
		return totalTime;
	}

	private static long arrayListIterator(DataGen dataGen) {
		List<String> l = dataGen.gen();
		
		for(int i = 0; i< DATA_SIZE; i++) {
			l.add("foo " + i);
		}

//		System.out.println("Begin Size is " + l.size());
		
		long start = System.currentTimeMillis();
		for(Iterator<String> iter = l.iterator(); iter.hasNext(); ) {
			iter.next();
			iter.remove();
		}
		long time = System.currentTimeMillis() - start;
		
//		System.out.println("End Size is " + l.size());
		
		return time;
	}

}
