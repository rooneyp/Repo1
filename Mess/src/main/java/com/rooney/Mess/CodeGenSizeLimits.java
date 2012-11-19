package com.rooney.Mess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class CodeGenSizeLimits {
    public static void main(String[] args) throws Exception {
        buildJavaClassCode(100, 100);
        execCompileAndRun();
    }
    
    private static void execCompileAndRun() throws Exception {
        execCmd(new String[]{"javac", "-J-Xmx4G",  "\\Foo.java"});
        execCmd(new String[]{"java", "-cp",  "\\", "Foo"});
        System.out.println("Finished execCompileAndRun");
        
    }

    public static void execCmd(String[] cmd) throws IOException {
        Process proc = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        
        String aLine;
        while ((aLine = reader.readLine()) != null) {
            System.out.println(aLine);
        }
        reader.close();
    }

    static class CountingPrintWriter extends PrintWriter {
        int lineCount = 0;
        public CountingPrintWriter(File file) throws FileNotFoundException {
            super(file);
        }

        @Override
        public void println(String x) {
            super.println(x);
            lineCount++;
        }
    }
    
    private static void buildJavaClassCode(int linesOfCodePerMethod, int numOfMethods) throws Exception {
        
        File file = new File("\\Foo.java");
        CountingPrintWriter pw = new CountingPrintWriter(file);
               
//        pw.println("package com.rooney.mess;");
        pw.println("public class Foo {");
        for (int i = 0; i < numOfMethods; i++) {
            pw.println("int a" + i + ";");
        }
        
        for (int i = 0; i < numOfMethods; i++) {
            writeMethod(pw, "bar" + i, linesOfCodePerMethod);
        }
        pw.println("public static void main(String[] args) throws Exception {");
        pw.println("Foo foo = new Foo();");
        pw.println("System.out.println(\"begin calling methods\");");
        for (int i = 0; i < numOfMethods; i++) {
            pw.println("foo.bar" + i + "();");
        }
        pw.println("System.out.println(\"finished calling methods. linesOfCode="+ linesOfCodePerMethod + " numOfMethods = " 
                    + numOfMethods + " totalLines= " + new DecimalFormat("###,###,###,###,###,###").format(pw.lineCount) + "\");");
        pw.println("}");
        pw.println("}");
        pw.close();
        System.out.println("Finished buildJavaClassCode");
    }

    private static void writeMethod(PrintWriter pw, String methodName, long linesOfCode) {
        pw.println("\tpublic void " + methodName + "() throws Exception {");
        pw.println("int sb = 0;");
        for (int i = 0; i < linesOfCode; i++) {
            pw.println("sb = " + i + ";");
        }
        pw.println("\t}");
    }
    
}
