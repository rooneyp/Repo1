package com.rooney.Mess;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Queues {

    BlockingQueue<Integer> queue;
    volatile boolean keepGoing = true;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new Queues().go();
    }

    private void go() throws Exception {
        queue = new ArrayBlockingQueue<Integer>(1000);

        Thread producer = new Producer("Prod");
        Thread consumer1 = new Consumer("Con1");
        Thread consumer2 = new Consumer("Con2");

        producer.start();
        consumer1.start();
        consumer2.start();

        producer.join();
        consumer1.join();
        consumer2.join();
    }

    class Producer extends Thread {

        public Producer(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 1; i <= 1000; i++) {
                try {
                    queue.put(i);
                    System.out.println("PUT " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            keepGoing = false;
        }
    }

    class Consumer extends Thread {
        public Consumer(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(this.getName() +" starting");
            Integer item = null;
            while (keepGoing || ( (item = queue.poll()) != null) ) {
                System.out.println(this.getName() + " GOT " + item);
            }
            System.out.println(this.getName() +" finishing");
        }
    }

}
