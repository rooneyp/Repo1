package com.rooney.Mess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
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

    private static void loadFileFromJar() throws Exception {
    	String csvLocation = "jar:file:/tmp/foo.jar!/adir/afile.CSV";
    	URL url = new URL(csvLocation);
		InputStream is = url.openStream();
		final BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		System.out.println(br.readLine());
		br.close();
    }
    
    private static void loadAllFilesFromDir() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for (File file : new File("/delme").listFiles()) {
            map.put(file.getName(), IOUtils.toString(file.toURI()));
        }
        
    }
}
