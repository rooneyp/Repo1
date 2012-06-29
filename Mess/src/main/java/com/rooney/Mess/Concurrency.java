package com.rooney.Mess;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Concurrency {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		basicBlockingQ();

	}

	private static void basicBlockingQ() throws InterruptedException {
		BlockingQueue<String> bq = new ArrayBlockingQueue<String>(20);
		
		bq.put("1");
		System.out.println(bq.remove());
	}

}
