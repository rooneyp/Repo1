package com.rooney.Mess;

import java.io.InputStream;

public class ReadFileFromClasspath {
	public static void main(String[] args) {
		InputStream is = ReadFileFromClasspath.class.getResourceAsStream("/bytel/001-units/units_Units.xml");
		String xml = new java.util.Scanner(is).useDelimiter("\\A").next();
		System.out.println(xml);
	}
}
