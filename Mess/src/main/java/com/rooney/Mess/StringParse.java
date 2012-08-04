package com.rooney.Mess;

import junit.framework.TestCase;

public class StringParse extends TestCase {

    void testStringArray() {
        String[] foo = { getA(), getA() };
    }

    private String getA() {
        return "A";
    }

    public static void testSplit() {
        String input = "A,B,C,D";

        String[] expectedOrder = { "A", "B", "C", "D" };

        String[] splitString = input.split(",");

        assertEquals(expectedOrder.length, splitString.length);

        for (int i = 0; i < expectedOrder.length; i++) {
            assertEquals(expectedOrder[i], splitString[i]);
        }
    }

}
