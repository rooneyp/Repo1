package com.rooney.Mess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class Files {
    public static void main(String[] args) throws Exception {
//        loadAllFilesFromDir(); 
        dosToUnixOnFile();
    }

    public static void dosToUnixOnFile() throws FileNotFoundException, IOException {
        FileInputStream inputStream = new FileInputStream("/ConcordionTestShTemplate.ftl");
        String everything;
        try {
            everything = IOUtils.toString(inputStream);
        } finally {
            inputStream.close();
        }

        FileOutputStream outputStream = new FileOutputStream("/ConcordionTestShTemplate_OUT.ftl");
        try {
            IOUtils.write(everything.replaceAll("\r", ""), outputStream);
        } finally {
            outputStream.close();
        }
    }

    private static void loadAllFilesFromDir() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for (File file : new File("/delme").listFiles()) {
            map.put(file.getName(), IOUtils.toString(file.toURI()));
        }
        
    }
}
