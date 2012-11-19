package com.rooney.Mess;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        List<String> strings = Arrays.asList("foo", "bar");

        foo(strings);
    }

    static void foo(List<String> names) {
        System.out.println(names);
    }
}
