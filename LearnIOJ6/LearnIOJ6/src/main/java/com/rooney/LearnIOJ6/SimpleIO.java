package com.rooney.LearnIOJ6;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class SimpleIO {
	int aInt = 999911999;
	long aLong = 99992222999L;
	float aFloat =  999922.22999f;
	double aDouble = 999944.44999d;
	String aString = "string_string";
	boolean aBoolean = true;

	public static void main(String[] args) throws Exception {
		SimpleIO simpleIO = new SimpleIO();
		
//		simpleIO.useWriter();
		simpleIO.useDataOutputStream();
	}

	private void useDataOutputStream() throws FileNotFoundException,
			IOException {
		FileOutputStream fos = new FileOutputStream("/tmp/WriteBoolean.bin");
	    DataOutputStream dos = new DataOutputStream(fos);
	    dos.writeBoolean(aBoolean);
	    dos.close();
	}

	private void useWriter() throws IOException {
		BufferedWriter writer =  new BufferedWriter(new FileWriter("/tmp/SimpleIO.txt"));
		writer.write(aDouble + "\n");
		writer.close();
	}
}
