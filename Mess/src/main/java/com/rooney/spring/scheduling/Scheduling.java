package com.rooney.spring.scheduling;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration
public class Scheduling {
    @Autowired TaskScheduler scheduler;
    
    @Test public void testScheduling() throws Exception {
        scheduler.schedule(new MyRunnable(10), new CronTrigger("*/5 * * * * ?"));
        
        scheduler.schedule(new MyRunnable(1), new CronTrigger("*/5 * * * * ?"));
//        scheduler.schedule(new MyRunnable(), new CronTrigger("0 */1 * * * ?")); //every min
        Thread.sleep(600000);
    }
    
    public static class MyRunnable implements Runnable {
        
        private int delay;

        public MyRunnable(int i) {
            this.delay = i;
        }

        public void run() {
            System.err.println(Thread.currentThread() + " delay:"+ delay +"\tRunning: " + new Date() + ", " + this);
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
