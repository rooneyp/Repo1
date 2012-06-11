package com.rooney.Mess;

public class ThreadInterrupts {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParentThread parent = new ParentThread();
		parent.start();
		
		try {
			Thread.sleep(10000);
			parent.interrupt();
		
			parent.join(); //carefull as child nothing is waiting on Child thread to finish!!
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
				Thread.sleep(2000);
				childThread.interrupt();
				Thread.sleep(2000);
				childThread.interrupt();
				childThread.join();
				
				Thread.sleep(5000); //wait for Main Thread to interrupt 				
			} catch (InterruptedException e) {
				System.out.println("Parent thread interrupted");
			}
		}
	}
	
	public static class ChildThread extends Thread {
		int interruptCount = 0;
		@Override
		public void run() {
			while(interruptCount <2) {
				try {
					Thread.sleep(10000);
					System.out.println("Child Thread hello [should never see this!!]");
				} catch (InterruptedException e) {
					System.out.println("Child thread interrupted");
					interruptCount++;
				}
			}
			
		}
	}	
}
