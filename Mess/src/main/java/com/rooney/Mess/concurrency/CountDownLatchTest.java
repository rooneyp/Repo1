package com.rooney.Mess.concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new CountDownLatchTest().testWaitNotify();
    }

    public void testWaitNotify() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1); // just one time
        Thread t = new Thread() {
            @Override
            public void run() {
                // no lock to acquire!
                System.out.println("Going to count down...");
                latch.countDown();
            }
        };

        t.start(); // start her up and let her wait()
        System.out.println("Going to await...");
        latch.await();
        System.out.println("Done waiting!");
    }

}
