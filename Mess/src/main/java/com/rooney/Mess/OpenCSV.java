package com.rooney.Mess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import au.com.bytecode.opencsv.CSVWriter;

public class OpenCSV {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        FileWriter fileWriter = new FileWriter("d:\\yourfile.csv");
        
        File file = new File("d:\\yourfile.csv");
        
        if (file.exists()) {
            System.err.println("file exists " + file);
        }

        OutputStreamWriter outputStreamWriter =
                new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "UTF-8");
        
        CSVWriter writer = new CSVWriter(new BufferedWriter(outputStreamWriter), ',');
        // feed in your array (or convert your data to an array)
        String[] entries = "first#second#third".split("#");
        writer.writeNext(entries);
        writer.close();
    }

}
