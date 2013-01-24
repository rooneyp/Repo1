package com.rooney.Mess;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Concurrency {
    //private volatile boolean keepGoing = true; //option A
    
	public static void main(String[] args) throws Exception {
//        new Concurrency().reentrantReadWriteLock();
	    new Concurrency().executorShutdown();
	}

	//try and shutdown all threads using the executor (and its interruptException)
	private void executorShutdown() throws Exception {
	    final int serverPort = 10000;
	    //try blocking socket read
	    
	    
	    ExecutorService  executor = Executors.newCachedThreadPool();
//	    executor.execute(new Runnable() {
	    Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("thread begin");
                //                while(keepGoing) {
                while (!Thread.currentThread().isInterrupted()) { //check flag. If we perform an blocking call we should catch and ignore Interrupted EX
                    try {
//                        com.rooney.Mess.MaxOutCpu.calcPiInefficiently(10000000L);
//                        TimeUnit.SECONDS.sleep(200); //throws InterruptedException
//                        new LinkedBlockingQueue<String>(1).take(); //throws InterruptedException
                        System.in.read(); //does not throw ex i.e. get interrupted. We need NIO & InterruptibleChannel
                        
//                         runSimpleSocketServer(serverPort);
//                         new Socket("localhost", serverPort).getInputStream().read(); // does not throw ex i.e. i.e. get interrupted We need NIO & InterruptibleChannel
                        
                        System.out.println("done some work");
                    } catch (Exception e) { //InterruptedException e) {
                        System.out.println("I've been interrupted: interrupt flag status is " + Thread.currentThread().isInterrupted());
                        Thread.currentThread().interrupt(); //need to reapply the interrupt flag
                    } 
                }
                System.out.println("thread end");
            }
            
             public void cancel() { Thread.currentThread().interrupt(); } //also an option if we don't wish to use executor.shutdownNow();

        });
	    t.start();
	    
	    TimeUnit.SECONDS.sleep(3);
	    System.out.println("shutting down executor"); //does nothing
//	    executor.shutdownNow(); // we could also have a shutfown method on the Runnable which
	    t.interrupt();
	    
	    System.out.println("main end");
	    
	}

    public void runSimpleSocketServer(final int serverPort) {
        new Thread() {
            public void run() {
                com.rooney.Mess.Sockets.simpleAccept(1000 * 60, serverPort);
            }
	    }.start();
    }
	
	
	/*****************************************************************/
    private void reentrantReadWriteLock() throws Exception {
        final ReadWriteLock rwl = new ReentrantReadWriteLock();

        ExecutorService executor = Executors.newCachedThreadPool();

        // share lock across threads
        Lock readLock = rwl.readLock();
        Lock writeLock = rwl.writeLock();


        runThread(LockType.READ, rwl, executor, 3000, 0, readLock);
        runThread(LockType.READ, rwl, executor, 3000, 0, readLock);
        // Thread.sleep(500); // want to delay the write lock aquistion as it will block read lock acquisitions
        // runThread(LockType.WRITE, rwl, executor, 3000, 1000, writeLock);
    }

    public enum LockType {
        READ, WRITE
    }

    public void runThread(final LockType lockType, final ReadWriteLock rwl, ExecutorService executor,
            final int sleepTimeMills, final int timeToWaitOnLockMillis, final Lock lock) {
        executor.execute(new Runnable() {
            public void run() {
                log(Thread.currentThread() + " acquiring Lock " + lockType);

                // Lock lock = null;
                // switch (lockType) {
                // case READ:
                // lock = rwl.readLock();
                // break;
                //
                // case WRITE:
                // lock = rwl.writeLock();
                // break;
                // }

                boolean lockAcquired = false;
                try {
                    try {
                        lockAcquired = lock.tryLock(timeToWaitOnLockMillis, TimeUnit.MILLISECONDS);
                        log(Thread.currentThread() + " lockAcquired=" + lockAcquired + ", type="
                                + lockType);
                        log(Thread.currentThread() + " sleeping for " + sleepTimeMills);
                        Thread.sleep(sleepTimeMills);
                    } catch (InterruptedException e) {
                        log(Thread.currentThread() + " interrupted");
                    }
                } finally {
                    if (lock != null && lockAcquired) {
                        lock.unlock();
                    }
                    log(Thread.currentThread() + " finished and unlocked " + lockType);
                }
            }

            private void log(String string) {
                System.out.println(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + " " + string);

            }
        });
    }

}
