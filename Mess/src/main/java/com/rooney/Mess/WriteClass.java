package com.rooney.Mess;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.io.FileUtils;

public class WriteClass {
    public static void main(String[] args) throws Exception {
//        oneNotWorking();
//        twoNotWorking();
//        ClassGen cGen = new ClassGen(new ClassParser(String.class).parse());
//        new BCELifier( 
    }

    public static void twoNotWorking() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        Class<String> clazz = String.class; 
        o.writeObject(clazz);
        
        FileUtils.writeByteArrayToFile(new File("/String.class"), b.toByteArray());
    }

    public static void oneNotWorking() throws IOException, FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/String.class"));
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            
        outputStream.writeObject(String.class);
        
        outputStream.flush();
        fileOutputStream.flush();
        
        outputStream.close();
        fileOutputStream.close();
    }
    
}
