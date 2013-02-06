package com.rooney.Mess;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class Files {
    public static void main(String[] args) throws Exception {
        loadAllFilesFromDir(); 
    }

    private static void loadAllFilesFromDir() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for (File file : new File("/delme").listFiles()) {
            map.put(file.getName(), IOUtils.toString(file.toURI()));
        }
        
    }
}
