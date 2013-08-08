package com.rooney.Mess.maps;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class MaxSizeDecoratorTest {

    @Test
    public void test() throws Exception {
        Thread.currentThread().setName("swapOut");
        
        final MaxSizeDecorator decorator = new MaxSizeDecorator(100);
        put(decorator, 4, 1000);
        
        swapOut(decorator, 5, 500);

        Thread.sleep(200 * 1000);
    }
    
    public void swapOut(final MaxSizeDecorator decorator, final int sleepSecs, int numOfTimes) throws Exception {
        for(int i=0; i< numOfTimes; i++) {
            Thread.sleep(1000 * sleepSecs);
            System.out.println(Thread.currentThread() + "\t swapOut awake");
            Map<String, String> swapOut = decorator.swapOut();
            System.out.println(Thread.currentThread() + "\t swapOut finished" + swapOut);
        }
    }
    
    public void put(final MaxSizeDecorator decorator, final int threads, final int putsPerThread) {
        for(int x=0; x<threads; x++) {
            new Thread("put-"+x) {
                public void run() {
                    System.out.println(Thread.currentThread() + "\\t putter putting");
                    
                    for(int i=0; i<putsPerThread; i++) {
                        decorator.put(""+i, ""+i);
                        if(i % 50 == 0) {
                            System.out.println("\t putter put " + i);
                        }
                    }
                    
                    System.out.println("\t putter finished");
                }
            }.start();
        }
    }

}
