package com.rooney.Mess.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.RandomStringUtils;

public class SynchronizedVsReentrantLock implements Runnable{
	DynaBean bean = new LazyDynaBean(new BasicDynaClass());
	String sampleBeanValue = "";
	int threadCount = 10;
	int randomValueCount = 100;
	int iterationCount = 1000;
	List<String> randomValues = new ArrayList<String>();
	AtomicInteger threadId = new AtomicInteger(1);

	public static void main(String[] args) throws Exception {
		new SynchronizedVsReentrantLock().doStuff();
	}

	
	private void doStuff() throws Exception {
		for(int i=0; i<randomValueCount; i++) {
			randomValues.add(RandomStringUtils.randomAlphabetic(10));
		}

		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		for(int i=0; i<threadCount; i++) {
			newCachedThreadPool.execute(this);
		}

		Thread.sleep(120 * 1000);
	}
	
	public void run() {
		Thread.currentThread().setName("" + threadId.getAndIncrement());
		System.out.println(Thread.currentThread().getName() + "BEGIN");
		
		for(int i = 0; i< iterationCount; i++) {
			getProperty(randomValues.get(i));
		}
		System.out.println(Thread.currentThread().getName() + "END");
	}
	
	public void getProperty(String propertyName) {
//		synchronized (this) {
			if(bean.get(propertyName) == null) {
				bean.set(propertyName, sampleBeanValue);
			}
//		}
	}


	private void mess() {
		System.out.println(bean.get("foo"));
		bean.set("foo", "I am Foo");
		
		System.out.println(bean.get("foo"));
	}
}
