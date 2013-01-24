package com.rooney.Mess;

public class MaxOutCpu extends Thread{
	private static volatile boolean keepRunning = true; 
	public MaxOutCpu(String name) {
		super(name);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		new Cpu("").go(Integer.valueOf(args[1]));
		new MaxOutCpu("").go(Integer.valueOf(8));
	}

	private void go(int threadCount) throws InterruptedException {
		for (int i = 0; i < threadCount; i++) {
			new MaxOutCpu("Thread-" + i).start();
		}
		
		sleep(1000 * 60 * 2);
		keepRunning = false;
		join();
	}

	@Override
	public void run() {
		System.out.println("Thread running" + Thread.currentThread());
		while(keepRunning) {
			calcPiInefficiently(1000000);
		}
		
	}

    public static void calcPiInefficiently(long numTerms) {
        // calc pi inefficiently
        double sum = 0.0; // final sum
        double term; // term without sign
        double sign = 1.0; // sign on each term
        for (int k = 0; k < numTerms; k++) {
        	term = 1.0 / (2.0 * k + 1.0);
        	sum = sum + sign * term;
        	//if (k % (N / 50) == 0) // print one in 50
        	// System.out.println ("k: " + k + ", " + sum + ", pi: " +
        	// sum*4.0);
        		sign = -sign;
        }
        // System.out.println("Final pi/4 (approx., " +
        // N + " terms): " + sum);
        // System.out.println("Actual pi/4: " +
        // Math.PI/4.0);
        // System.out.println("Final pi (approx., " +
        // N + " terms): " + sum*4.0);
        // System.out.println("Actual pi: " + Math.PI);
    }

	
	
	
}
