package com.rooney.Mess;

public class ThreadInterrupts {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParentThread parent = new ParentThread();
		parent.start();
		
		try {
			Thread.sleep(8000);
			parent.interrupt();
		
			parent.join();
		} catch (InterruptedException e) {
			System.out.println("Main thread Interrupted");
			e.printStackTrace();
		}

		System.out.println("main finishing");
	}
	
	public static class ParentThread extends Thread {
		@Override
		public void run() {
			try {
				System.out.println("Parent Thread hello");
				ChildThread childThread = new ChildThread();
				childThread.start();
				Thread.sleep(5000);
				childThread.interrupt();
				childThread.join();
				
				Thread.sleep(5000); //wait for Main Thread to interrupt 				
			} catch (InterruptedException e) {
				System.out.println("Parent thread interrupted");
			}
			
		}
	}
	
	public static class ChildThread extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(10000);
				System.out.println("Child Thread hello");
			} catch (InterruptedException e) {
				System.out.println("Child thread interrupted");
//				e.printStackTrace();
			}
			
		}
	}	
}
