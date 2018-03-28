package com.rooney.java8.learning_java8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class NoArgLambdaBooks {
	final Map<Integer, String> map = new ConcurrentHashMap<>();

	int add(String title) {
		System.out.println(System.nanoTime() + " " + Thread.currentThread().getName() + "\tRunning");
		final Integer next = this.map.size() + 1;
		this.map.put(next, title);
		return next;
	}

	String title(int id) {
		return this.map.get(id);
	}

	public static void main(String[] args) throws Exception {
		mainLatchToStartAllTogether(args);
	}

	public static void mainLatchToStartAllTogether(String[] args) throws Exception {
		NoArgLambdaBooks books = new NoArgLambdaBooks();

		CountDownLatch latch = new CountDownLatch(1);
		AtomicBoolean isRunning = new AtomicBoolean();
		AtomicInteger overlapsOccurred = new AtomicInteger();

		int threads = 100;
		ExecutorService service = Executors.newFixedThreadPool(threads);
		Collection<Future<Integer>> futures = new ArrayList<>(threads);

		for (int t = 0; t < threads; ++t) {
			final String title = String.format("Book #%d", t);
			futures.add(service.submit(() -> {
				latch.await();
				if (isRunning.get()) {
					overlapsOccurred.incrementAndGet();
				}
				isRunning.set(true);
				int id = books.add(title);
				isRunning.set(false);
				return id;
			}));
		}
		latch.countDown();
		Set<Integer> ids = new HashSet<>();
		for (Future<Integer> f : futures) {
			ids.add(f.get());
		}
		assert overlapsOccurred.get() > 0;
		System.out.println(overlapsOccurred);

		assert ids.size() == threads;
	}

	public static void mainBasicParallel(String[] args) throws Exception {
		NoArgLambdaBooks books = new NoArgLambdaBooks();

		int threads = 10;
		ExecutorService service = Executors.newFixedThreadPool(threads);
		Collection<Future<Integer>> futures = new ArrayList<>(threads);

		for (int t = 0; t < threads; ++t) {
			final String title = String.format("Book #%d", t);
			futures.add(service.submit(() -> books.add(title))); //no arg lambda for async exe
		}

		Set<Integer> ids = new HashSet<>();
		for (Future<Integer> f : futures) {
			ids.add(f.get());
		}

		assert ids.size() == threads;
	}

}
