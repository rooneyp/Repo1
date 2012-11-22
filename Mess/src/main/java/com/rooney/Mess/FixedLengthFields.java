package com.rooney.Mess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FixedLengthFields {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        long someLong = 100;
        convertLongUsingBaosAndDos(someLong);
        convertLongUsingByteBuffer(someLong);
    }

    private static void convertLongUsingByteBuffer(long someLong) {
        byte[] longBytes = ByteBuffer.allocate(8).putLong(someLong).array(); //do we have to say 8?
        System.out.println("ByteBuffer: long=" + someLong + " num of bytes is " + longBytes.length);
        System.out.println("Stored: " + someLong + " retrieved  " + ByteBuffer.wrap(longBytes).getLong());
    }

    public static void convertLongUsingBaosAndDos(long someLong) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeLong(someLong);
        dos.close();
        byte[] longBytes = baos.toByteArray();
        System.out.println("DAO backed by BAOS: long=" + someLong + " num of bytes is " + longBytes.length);
        
        ByteArrayInputStream b = new ByteArrayInputStream(longBytes);
        DataInputStream o = new DataInputStream(b);
        System.out.println("Stored: " + someLong + " retrieved  " + o.readLong()); 
    }

}
