package com.rooney.Mess;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

public class AllocAndFree {

    private static final int NUM_RECS = 1000000;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        List<String> data = new ArrayList<String>(NUM_RECS);

        for (int i = 0; i < 100000; i++) {
            alloc(data);
            data.clear();
            if (i % 30 == 0) {
                System.out.println(i);
            }
        }

    }

    public static void alloc(List<String> data) throws Exception {
        for (int i = 0; i < NUM_RECS; i++) {
            data.add(RandomStringUtils.randomAscii(100));
        }
        Thread.sleep(500);
    }

}
