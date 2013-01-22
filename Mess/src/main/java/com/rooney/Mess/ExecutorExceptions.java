package com.rooney.Mess;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorExceptions {

    /**
     * Can i have a nice way to detect DC/exe pipeline having problems 
     * so shutdown down 
     * 
     * how about handler which on one ex, shutsdown the executor?
     * 
     * you can u query a future's on after done method
     * 
     * maybe all I want is a default error logger wrapper/delegator Callable/Runnable
     * @param args
     */
    public static void main(String[] args) {
        basic();
    }

    public static void basic() {
        System.out.println("START");
        ExecutorService executor = Executors.newCachedThreadPool();
        
        executor.execute(new MyRunnable("run1")); // ex thrown to jvm
        
        Future run1Future = executor.submit(new MyRunnable("run2")); //) 
        
        try {
            run1Future.get(); //bl
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
        System.out.println("END");
    }

    // allows ex to be passed back
    public static class MyCallable implements Callable {
        public Object call() throws Exception {
            return null;
        }
        
    }
    
    public static class MyRunnable implements Runnable {
        private String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        public void run() {
            Thread.currentThread().setName(name);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " MyRunnable.run");
            throw new RuntimeException(name + " RTE");
        }
    }
}
