package com.rooney.Mess;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class ThreadInterrupts {
	public static void main(String[] args) {
		Thread childThread = null;
		switch (Integer.valueOf(args[0])) {
			case 1:
				childThread = new SimpleChildThread();
				break;
			case 2:
				childThread = new BlockedReaderChildThread();
				break;
			case 3:
				childThread = new InterruptibleReaderChild();
				System.out.println("warning: InterruptibleReaderChild will not work in eclipse!!!!");
				break;
		}

		ParentThread parent = new ParentThread(childThread);
		parent.start();

		try {
			Thread.sleep(10000);
			parent.interrupt();
			parent.join(); // careful, nothing is waiting on Child thread to finish. if it isn't managed it will stay running!!
		} catch (InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}
		System.out.println("main finishing");
	}

	public static class ParentThread extends Thread {
		private Thread childThread;

		public ParentThread(Thread childThread) {
			this.childThread = childThread;
		}

		public void run() {
			try {
				System.out.println("Parent Thread Begin");
				childThread.start();

				Thread.sleep(2000);
				childThread.interrupt();
				Thread.sleep(2000);
				childThread.interrupt();

				childThread.join();

				Thread.sleep(60000); // wait for Main Thread to interrupt
			} catch (InterruptedException e) {
				System.out.println("Parent thread interrupted");
			}
			System.out.println("ParentThread finishing");
		}
	}

	public static class SimpleChildThread extends Thread {
		int interruptCount = 0;

		public void run() {
			while (interruptCount < 2) {
				try {
					System.out.println("SimpleChildThread sleeping");
					Thread.sleep(10000);
					System.out.println("SimpleChildThread hello [should never see this!!]");
				} catch (InterruptedException e) {
					System.out.println("SimpleChildThread interrupted");
					interruptCount++;
				}
			}
			System.out.println("SimpleChildThread finishing");
		}
	}

	//InputStream.read is not interruptible
	public static class BlockedReaderChildThread extends Thread {
		public void run() {
			int interruptCount = 0;
			while (interruptCount < 2) {
				try {
					while (true) {
						System.out.println("BlockedReaderChildThread begin read");
						System.in.read(new byte[512]);
						System.out.println("BlockedReaderChildThread end read");
					}
				} catch (Exception e) {
					e.printStackTrace();
					interruptCount++;
				}
			}
			System.out.println("BlockedReaderChildThread finishing");
		}
	}

	//Any InputStream can be wrapped and made Interruptible
	public static class InterruptibleReaderChild extends Thread {
		public void run() {
			int interruptCount = 0;
//			InputStream interruptibleInputStream = Channels.newInputStream((new FileInputStream(FileDescriptor.in)).getChannel()); // shortcut
//			InputStream anInputStream = new FileInputStream(FileDescriptor.in); //long hand
//			ReadableByteChannel readableByteChannel = Channels.newChannel(anInputStream); //gives Interruptible

//			InputStream interruptibleInputStream = Channels.newInputStream( Channels.newChannel(System.in)); //shortcut
			ReadableByteChannel readableByteChannel = Channels.newChannel(System.in); //gives Interruptible
			InputStream interruptibleInputStream = Channels.newInputStream(readableByteChannel);
			
			while (interruptCount < 2) {
				try {
					System.out.println("StreamReadChildThread begin read");
					interruptibleInputStream.read(new byte[512]);
					System.out.println("StreamReadChildThread end read");
				} catch (Exception e) {
					e.printStackTrace();
					interruptCount++;
				}
			}
			System.out.println("InterruptibleReaderChild finishing");
		}
	}
}
