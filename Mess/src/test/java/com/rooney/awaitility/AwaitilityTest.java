package com.rooney.awaitility;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Callable;

import org.junit.Test;

/*
//ConditionFactory is immutable, each fluent method call, returns a new CF with all current settings except those overridden by the fluent method
ConditionFactory.until(Callable<Boolean> conditionEvaluator) //rets void
	until(new CallableCondition(conditionEvaluator, generateConditionSettings()));
			//ConditionSettings is a param obj to take values from CF: alias, catchUncaughtExceptions, timeout, pollInterval, pollDelay
			//CallableCondition ctors anon impl of abstract ****ConditionAwaiter****
			// abstract ctor:
				 this.latch = new CountDownLatch(1);
				 ctor new 'Runnable' poller: if (condition.call()) {latch.countDown();
				 run poller indefinitly with: executor.scheduleWithFixedDelay(poller, conditionSettings.getPollInterval(), ...  //exector is Executors.newScheduledThreadPool(1)
				 
		condition.await();
			 finishedBeforeTimeout = latch.await(conditionSettings.getMaxWaitTime())
			 if(finishedBeforeTimeout) //all good
			 else throw ex
			 finally : executor.shutdown();
*/			 
public class AwaitilityTest {
	private int counter = 0;
	
	@Test
	public void test() {
		new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				counter = 1;
			}
			
		}.start();
		
		await().atMost(5, SECONDS).until(newUserIsAdded());
	}

	
	private Callable<Boolean> newUserIsAdded() {
        return new Callable<Boolean>() {
                public Boolean call() throws Exception {
                	System.out.println("called " + System.currentTimeMillis());
                    return counter == 1; // The condition that must be fulfilled
                }
        };
}
}
