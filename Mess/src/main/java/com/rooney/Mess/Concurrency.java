package com.rooney.Mess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Concurrency {

	public static void main(String[] args) throws Exception {
        new Concurrency().reentrantReadWriteLock();
	}

    private void reentrantReadWriteLock() throws Exception {
        final ReadWriteLock rwl = new ReentrantReadWriteLock();

        ExecutorService executor = Executors.newCachedThreadPool();

        runThread(LockType.READ, rwl, executor, 3000, 0);
        runThread(LockType.READ, rwl, executor, 3000, 0);
        Thread.sleep(500); // want to delay the write lock aquistion as it will block read lock acquisitions
        runThread(LockType.WRITE, rwl, executor, 3000, 1000);
    }

    public enum LockType {
        READ, WRITE
    }

    public void runThread(final LockType lockType, final ReadWriteLock rwl, ExecutorService executor,
            final int sleepTimeMills, final int timeToWaitOnLockMillis) {
        executor.execute(new Runnable() {
            public void run() {
                log(Thread.currentThread() + " acquiring Lock " + lockType);

                Lock lock = null;
                switch (lockType) {
                    case READ:
                        lock = rwl.readLock();
                        break;

                    case WRITE:
                        lock = rwl.writeLock();
                        break;
                }

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
