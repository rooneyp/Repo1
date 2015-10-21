package chapters.introduction;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class SimpleMDC {
    static public void main(String[] args) throws Exception {

        // You can put values in the MDC at any time. Before anything else we put the first name
        MDC.put("first", "Dorothy");

        Logger logger = LoggerFactory.getLogger(SimpleMDC.class);
        // We now put the last name
        MDC.put("last", "Parker");

        // The most beautiful two words in the English language according to Dorothy Parker:
        logger.info("Check enclosed.");
        logger.debug("The most beautiful two words in English.");

        MDC.put("first", "Richard");
        MDC.put("last", "Nixon");
        logger.info("I am not a crook.");
        logger.info("Attributed to the former US president. 17 Nov 1973.");

        ExecutorService executor = Executors.newCachedThreadPool();
        
        //This shows only 2 different MDCs being using. As MDC is only propagated on thread creation
        for(int i=0; i<5; i++) {
            MDC.put("first", "dumb" + i);
            MDC.put("last", "dumb" + i);
            
            executor.execute(new DumbMDCRunnableImplementation());
        }
        
        //This shows only 2 different MDCs being using. As MDC is only propagated on thread creation
        for(int i=0; i<5; i++) {
            MDC.put("first", "explicit" + i);
            MDC.put("last", "explicit" + i);
            
            Map<String, String> mdcContext = MDC.getCopyOfContextMap();
            executor.execute(new ExplicitMDCRunnableImplementation(mdcContext));
        }
        
        
        executor = ExecutorServiceMdcDecorator.decorate(executor);
        
        //This shows auto propagation of MDC by decorating ExecutorService
        for(int i=0; i<5; i++) {
            MDC.put("first", "decorated" + i);
            MDC.put("last", "decorated" + i);
            
            executor.execute(new DumbMDCRunnableImplementation());
        }        

        Thread.sleep(1000 * 10);
    }

    private static final class DumbMDCRunnableImplementation implements Runnable {
        public void run() {
            Logger logger = LoggerFactory.getLogger(chapters.introduction.SimpleMDC.DumbMDCRunnableImplementation.class);
            logger.info("Running in a different thread");
        }
    }
    
    private static final class ExplicitMDCRunnableImplementation implements Runnable {
        private Map<String, String> mdcContext;

        public ExplicitMDCRunnableImplementation(Map<String, String> mdcContext) {
            this.mdcContext = mdcContext;
        }

        public void run() {
            MDC.setContextMap(mdcContext);
            Logger logger = LoggerFactory.getLogger(chapters.introduction.SimpleMDC.ExplicitMDCRunnableImplementation.class);
            logger.info("Running in a different thread");
        }
    }
    
    public static class ExecutorServiceMdcDecorator implements ExecutorService{
        private ExecutorService executor;
        
        private ExecutorServiceMdcDecorator(ExecutorService executor) {
            this.executor = executor;
        }
        
        public static ExecutorService decorate(ExecutorService executor) {
            return new ExecutorServiceMdcDecorator(executor);
        }

        public void execute(final Runnable command) {
            final Map<String, String> mdcContext = MDC.getCopyOfContextMap();
            
            executor.execute(new Runnable() {
                public void run() {
                    MDC.setContextMap(mdcContext);
                    command.run();
                }
            });
        }

        public void shutdown() {
            executor.shutdown();
        }

        public List<Runnable> shutdownNow() {
            return executor.shutdownNow();
        }

        public boolean isShutdown() {
            return executor.isShutdown();
        }

        public boolean isTerminated() {
            return executor.isTerminated();
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return executor.awaitTermination(timeout, unit);
        }

        public <T> Future<T> submit(Callable<T> task) {
            return executor.submit(task);
        }

        public <T> Future<T> submit(Runnable task, T result) {
            return executor.submit(task, result);
        }

        public Future<?> submit(Runnable task) {
            return executor.submit(task);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return executor.invokeAll(tasks);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return executor.invokeAll(tasks, timeout, unit);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return executor.invokeAny(tasks);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return executor.invokeAny(tasks, timeout, unit);
        }
    }
    
}
