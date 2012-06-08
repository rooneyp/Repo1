package com.rooney.Mess;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Utf8Test {
	public static void main(String[] args) throws Exception {
		BufferedWriter noBOM = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:\\euro_utf8_NoBOM.csv"),"UTF8"));
		String str = "\u20AC,\u20AC"; //"euro-symbol, euro-symbol"
		noBOM.write(str);
		noBOM.close();
		
		BufferedWriter withBOM = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:\\euro_utf8_BOM.csv"),"UTF8"));
		String strWithBOM = "\uFEFF" + str; //explicitly write BOM bytes at beginning of file
		withBOM.write(strWithBOM);
		withBOM.close();
		
		BufferedWriter defaultEncoding = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:\\euro_default_encoding.csv")));
		defaultEncoding.write(str);
		defaultEncoding.close();
	}
}
