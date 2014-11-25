package com.rooney.Mess.dates;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DatesMess {
	public static void main(String[] args) {
		
		//Date is UTC millis
		Date date = new Date();
		System.out.println("default Date toString: " + date);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println("default Timestamp toString: " + timestamp);
		
		Calendar calendar = new GregorianCalendar();
		System.out.println("default Calendar toString: " + calendar);
		
		calendar.setTimeZone(TimeZone.getTimeZone("Europe/Copenhagen"));
		System.out.println("Copenhagen TZ Calendar toString: " + calendar);
		
	}
}
