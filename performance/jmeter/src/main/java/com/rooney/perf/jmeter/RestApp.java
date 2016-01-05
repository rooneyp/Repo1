package com.rooney.perf.jmeter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class RestApp {
	AtomicLong stateId = new AtomicLong(1);
	AtomicLong workedId = new AtomicLong(1);
	
	private ExecutorService executor;
	Map<Long, List<Future<Worker>>> ongoingWork = new ConcurrentHashMap<Long, List<Future<Worker>>>();
	Map<Long, CompletionService<Worker>> completionSvcs = new ConcurrentHashMap<Long, CompletionService<Worker>>();
	
	public RestApp() {
		executor = Executors.newFixedThreadPool(8);
	}

	@RequestMapping(value = "/restapp/createcontext", method = RequestMethod.GET)
    public Long createContext(@RequestParam(value="text", required=false) String text) {
		Long newStateId = stateId.getAndIncrement();
		System.out.println("createContext " + newStateId + "**************** " + text);
		
		ongoingWork.put(newStateId, Collections.synchronizedList(new ArrayList<Future<Worker>>()));
		completionSvcs.put(newStateId, new ExecutorCompletionService<Worker>(executor));
        return newStateId;
    }

    @RequestMapping(value = "/restapp/submit/{id}", method = RequestMethod.GET)
    public void submitWork(@PathVariable Long id, @RequestParam(value="text", required=false) String text) {
        System.out.println("work received " + id + "**************** " + text);
        Future<Worker> submit = completionSvcs.get(id).submit(new Worker("" + id + ":" + workedId.getAndIncrement()));
        ongoingWork.get(id).add(submit);
    }

    @RequestMapping(value = "/restapp/endcontext/{id}", method = RequestMethod.GET)
    public void endContext(@PathVariable Long id, @RequestParam(value="text", required=false) String text) throws Exception {
        System.out.println("end context " + id + "**************** " + text);
        
        List<Future<Worker>> workers = ongoingWork.remove(id);
        CompletionService<Worker> completionSvc = completionSvcs.get(id);
        
        
        for(int i = 1; i <= workers.size(); i++) {
        	try {
                Worker r = completionSvc.take().get();
                if(r != null) {
                	System.out.println("Worker complete " + r.result);
                } 
            } catch (ExecutionException ignore) {
            	System.err.println(ignore);
            }
        }
    }  
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(RestApp.class, args);
    }

    
    
    public static class Worker implements Callable {
    	long sleep = 3000L;
    	String workerId;
    	String result;

		public Worker(String workerId) {
			this.workerId = workerId;
		}


		@Override
		public Object call() throws Exception {
			System.out.println(Thread.currentThread() + " Work Start");
			Thread.sleep(sleep + new Random(sleep).nextInt());
			System.out.println(Thread.currentThread() + " Work Complete");
			result = workerId + "COMPLETE";
			return this;
		}
    	
    }
}
