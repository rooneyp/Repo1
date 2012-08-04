package com.rooney.Mess;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class TestWriters {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Writer outputWriter =
                new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream, Charset.defaultCharset()));

        outputWriter.write("Foo");
        outputWriter.write("Bar");
        outputWriter.flush();
        System.out.println(byteArrayOutputStream.toString());
    }


}
