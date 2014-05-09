package com.rooney.sts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by paul on 28/04/2014.
 */
public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    public static final int TIMEOUT = 1000;
    private List<Philosopher> philosopherList = new ArrayList<Philosopher>();

    public static void main(String []args) throws InterruptedException {
        DiningPhilosophers diningPhilosophers = DiningPhilosophers.build();
//        System.out.println(philosophers);

        diningPhilosophers.begin();

        Thread.sleep(1000 * 10);

        diningPhilosophers.end();

        System.out.println("Finished");
    }

    private DiningPhilosophers() {};
    
    public static DiningPhilosophers build() {
        List<Fork> forks = new ArrayList<Fork>();
        for(char c='A';  c< 'A' + NUM_PHILOSOPHERS; c++) {
            forks.add(new Fork(""+c, TIMEOUT));
        }

        List<Philosopher> philosopherList = new ArrayList<Philosopher>();

        for(int i=1; i<= NUM_PHILOSOPHERS; i++) {
            philosopherList.add(new Philosopher("" + i, forks.get(i-1), i == 1 ? forks.get(NUM_PHILOSOPHERS -1): forks.get(i-2)));
        }

        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();
        diningPhilosophers.setPhilosopherList(philosopherList);

        return diningPhilosophers;
    }

    public void begin() {
    ExecutorService executorService = Executors.newFixedThreadPool(NUM_PHILOSOPHERS);
        for (Philosopher philosopher : philosopherList) {
            executorService.submit(philosopher);
        }

    }

    public void end() {
        for (Philosopher philosopher : philosopherList) {
            philosopher.stop();
        }
    }

    public List<Philosopher> getPhilosopherList() {
        return philosopherList;
    }

    public void setPhilosopherList(List<Philosopher> philosopherList) {
        this.philosopherList = philosopherList;
    }


    //
    public static class Fork{
        private String name;
        private final int timeout;
        private Semaphore forkLock = new Semaphore(1);

        public Fork(String name, int timeout) {
            this.name = name;
            this.timeout = timeout;
        }

        public boolean pickUp() {
            boolean acquired = false;
            try {
                acquired = forkLock.tryAcquire(timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return acquired;
        }

        public void putDown() {
            forkLock.release();
        }

        @Override
        public String toString() {
            return "Fork{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static class Philosopher implements Callable<String> {
        private String name;
        private Fork leftFork;
        private Fork rightFork;
        private volatile boolean keepGoing = true;

        public Philosopher(String name, Fork leftFork, Fork rightFork) {
            this.name = name;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        public void stop() {
            keepGoing = false;
        }

        @Override
        public String call() {
            while(keepGoing) {
                try {
                    System.out.printf("Philosopher %s acquiring left fork %s", name, leftFork.toString());
                    if(leftFork.pickUp()) {
                        System.out.printf("Philosopher %s ACQUIRED left fork %s", name, leftFork.toString());

                        System.out.printf("Philosopher %s acquiring right fork %s", name, rightFork.toString());
                        if(rightFork.pickUp()) {
                            System.out.printf("Philosopher %s Eating for 2s\n", name);
                            Thread.sleep(2000);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    leftFork.putDown();
                    rightFork.putDown();
                }

            }
            return "";
        }

        @Override
        public String toString() {
            return "Philosopher{" +
                    "name='" + name + '\'' +
                    ", leftFork=" + leftFork +
                    ", rightFork=" + rightFork +
                    '}';
        }
    }
}
