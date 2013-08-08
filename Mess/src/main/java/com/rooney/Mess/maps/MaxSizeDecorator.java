package com.rooney.Mess.maps;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * how to block puts with Map hits a max limit. And want to 
 * This is missing a ReentrantReadWriteLock
 * @author prooney
 *
 */
public class MaxSizeDecorator implements Map<String, String>{
    private int maxSize;
    private volatile CountDownLatch maxSizeLatch;  
    private volatile Map<String, String> internalStorage;

    public MaxSizeDecorator(int maxSize) {
        this.maxSize = maxSize;
        maxSizeLatch = new CountDownLatch(1);
        internalStorage = new ConcurrentHashMap<String, String>();
        System.out.println(maxSizeLatch + ", " + internalStorage);
    }

    public CountDownLatch getMaxSizeLatchReference() {
        CountDownLatch maxSizeLatchRef = maxSizeLatch;
        return maxSizeLatchRef;
    }
    
    

    public String put(String key, String value) {
//        System.out.println(Thread.currentThread() + " putting " + key);
        if(internalStorage.size() >= maxSize) {
            try {
                System.out.println(Thread.currentThread() + " put() waiting");
                CountDownLatch maxSizeLatchReference = getMaxSizeLatchReference();
                maxSizeLatchReference.await();
                System.out.println(Thread.currentThread() + " put() finished waiting");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return internalStorage.put(key, value);
    }
    
    public Map<String, String> swapOut() { //TODO assess risk of 'happens before'
        Map<String, String> returnValue = internalStorage; //store ref to curr instance of intStorage
        internalStorage = new ConcurrentHashMap<String, String>(); //swap in new instance
        
        CountDownLatch maxSizeLatchRef = getMaxSizeLatchReference(); //store ref to curr instance of max size latch
        
        maxSizeLatch = new CountDownLatch(1); // swap in new latch and set to 1 so new calls will use this instance
        
        maxSizeLatchRef.countDown(); //free up any threads waiting to put data
        
        return returnValue;
    }
    
    
    public int size() {
        return internalStorage.size();
    }

    public boolean isEmpty() {
        return internalStorage.isEmpty();
    }

    public boolean containsKey(Object key) {
        return internalStorage.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return internalStorage.containsValue(value);
    }

    public String get(Object key) {
        return internalStorage.get(key);
    }


    public String remove(Object key) {
        return internalStorage.remove(key);
    }

    public void putAll(Map<? extends String, ? extends String> m) {
        internalStorage.putAll(m);
    }

    public void clear() {
        internalStorage.clear();
    }

    public Set<String> keySet() {
        return internalStorage.keySet();
    }

    public Collection<String> values() {
        return internalStorage.values();
    }

    public Set<java.util.Map.Entry<String, String>> entrySet() {
        return internalStorage.entrySet();
    }

    public boolean equals(Object o) {
        return internalStorage.equals(o);
    }

    public int hashCode() {
        return internalStorage.hashCode();
    }
    
}
