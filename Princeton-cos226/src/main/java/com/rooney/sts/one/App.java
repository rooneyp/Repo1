package com.rooney.sts.one;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        int N = 8;
        String s = "";
        for (int n = N; n > 0; n /= 2) {
            System.out.println(" n: " + n + ", n/=2: " + (n/=2));
            s = (n % 2) + s;

        }
        System.out.println(s);
    }
}
