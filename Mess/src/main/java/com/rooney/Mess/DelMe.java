package com.rooney.Mess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DelMe {

    public static void main(String[] args) throws Exception {
        new Thread(){
            public void run() {
            }
        }.start();

        System.out.println("main sleeping");
        
        Thread.sleep(10000);
        
//        new Child().doStuff();
    }

    public static class Parent {
        private int parentVar = 100;
        
        public void doStuff() {
            System.out.println("this.parentVar " + this.parentVar);
        }
    }

    public static class Child extends Parent{
        
        @Override
        public void doStuff() {
            System.out.println("Calling parent");
            super.doStuff();
        }
    }    
    
    
    public  void loopWithChannel() throws Exception{
        int loopCount = 2;
        String obj = "I'm an Object";

        // write out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oas = new ObjectOutputStream(baos);

        for(int i=0; i<loopCount; i++) {
            oas.writeObject(obj + i);
        }
        
        byte[] data = baos.toByteArray();

        // read in
        ByteArrayInputStream bais = new ByteArrayInputStream(data); // could be from socket.getInputStream()
        
        ObjectInputStream ois = new ObjectInputStream(bais); // 

        for(int i=0; i<loopCount; i++) {
            System.out.println(ois.readObject());
        }
    }
    
    
    //Map<String, Object>
    public  void multiMap() throws IOException, ClassNotFoundException {
        int loopCount = 2;
        String obj = "I'm an Object";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("foo", obj);

        // write out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oas = new ObjectOutputStream(baos);

        for(int i=0; i<loopCount; i++) {
            oas.writeObject(map);
        }
        
        byte[] data = baos.toByteArray();

        // read in
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        for(int i=0; i<=loopCount; i++) {
            System.out.println((Map<String, Object>)ois.readObject());
        }
    }    

    
    
    
    public  void multiObj() throws IOException, ClassNotFoundException {
        int loopCount = 2;
        String obj = "I'm an Object";

        // write out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oas = new ObjectOutputStream(baos);

        for(int i=0; i<loopCount; i++) {
            oas.writeObject(obj + i);
        }
        
        byte[] data = baos.toByteArray();

        // read in
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        for(int i=0; i<loopCount; i++) {
            System.out.println(ois.readObject());
        }
    }    
    
    public  void singleObj() throws IOException, ClassNotFoundException {
        String obj = "I'm an Object";

        // write out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oas = new ObjectOutputStream(baos);

        oas.writeObject(obj);
        byte[] data = baos.toByteArray();

        // read in
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        System.out.println(ois.readObject());
    }

}
