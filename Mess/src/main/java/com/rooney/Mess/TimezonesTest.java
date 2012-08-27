package com.rooney.Mess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

public class TimezonesTest {

    @Test
    public void test() throws Exception {
        // yyyymmddhh24miss

        String format = new SimpleDateFormat("yyyyMMddkkmmss z").format(0);
        System.out.println(format);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddkkmmss z");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println(simpleDateFormat.format(0));
    }


    void timezones() {
        TimeZone cetTimeZone = TimeZone.getTimeZone("CET");
        System.out.println(cetTimeZone);
        Calendar cetCalendar = Calendar.getInstance(cetTimeZone);
        long cestTimeInMillis = cetCalendar.getTimeInMillis();

        // Thread.sleep(5);

        long utcTimeInMillis = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();

        System.out.println(utcTimeInMillis - cestTimeInMillis);
    }

}
