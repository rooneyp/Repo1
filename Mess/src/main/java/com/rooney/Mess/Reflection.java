package com.rooney.Mess;

public class Reflection {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // new Child().run();

        System.out.println(Child.class.getDeclaredMethods().length);

    }

    public static class Parent implements Runnable {

        public void run() {
            System.out.println("run");
        }
    }

    public static class Child extends Parent {

    }
}
