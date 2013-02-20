package com.rooney.spring.scheduling;

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
        scheduler.schedule(new MyRunnable(), new CronTrigger("*/5 * * * * ?"));
        Thread.sleep(10000);
    }
    
    public static class MyRunnable implements Runnable {
        public void run() {
            System.out.println("Running: " + this);
        }
    }
}
