package com.rooney.spark;

import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;

import java.io.File;
import java.io.IOException;

/**
 * To run:
 * gradlew assemble
 * \netqos\Dev\Apps\spark-1.0.0-bin-hadoop2\bin\spark-submit.cmd --class "com.rooney.spark.QuickStart" --master local[4] build\libs\StreamProcessing-1.0.jar
 *
 * Sources are Scala
 *  C:\Users\prooney\.gradle\caches\modules-2\files-2.1\org.apache.spark\spark-core_2.10\1.0.0\62d80f115146c176222a5a920983582b4a6ba466\spark-core_2.10-1.0.0-sources.jar\org\apache\spark\api\java\
 */
public class QuickStart {
    public static void main(String[] args) throws InterruptedException {
        hadoopOnWindowsHack();

        String logFile = "/netqos/Dev/Apps/spark-1.0.0-bin-hadoop2/README.md"; // Should be some file on your system
        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> logData = sc.textFile(logFile).cache();

        for (int i = 0; i < 100; i++) {
            doSomething(logData);
            Thread.sleep(1000);
        }
    }

    private static void doSomething(JavaRDD<String> logData) {
        long numAs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) { return s.contains("a"); }
        }).count();

        long numBs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) { return s.contains("b"); }
        }).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
    }

    /**
     * See https://issues.cloudera.org/browse/DISTRO-544
     * Resolves Error:
     * 14/06/11 10:50:52 ERROR Shell: Failed to locate the winutils binary in the hadoop binary path
     java.io.IOException: Could not locate executable null\bin\winutils.exe in the Hadoop binaries.
     at org.apache.hadoop.util.Shell.getQualifiedBinPath(Shell.java:278)
     at org.apache.hadoop.util.Shell.getWinUtilsPath(Shell.java:300)
     at org.apache.hadoop.util.Shell.<clinit>(Shell.java:293)
     at org.apache.hadoop.util.StringUtils.<clinit>(StringUtils.java:76)
     at org.apache.hadoop.mapred.FileInputFormat.setInputPaths(FileInputFormat.java:362)
     at org.apache.spark.SparkContext$$anonfun$22.apply(SparkContext.scala:546)
     *
     * OR maybe try binaries from
     * https://github.com/srccodes/hadoop-common-2.2.0-bin
     * May need Microsoft Visual C++ 2010 Redistributable Package (x64)
     * http://www.microsoft.com/en-us/download/details.aspx?id=14632
     *
     */
    private static void hadoopOnWindowsHack() {
        try {
            new File("./bin").mkdirs();
            File file = new File("./bin/winutils.exe");
            file.createNewFile();
            System.out.println("hadoopOnWindowsHack: WRITING DUMMY FILE " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File workaround = new File(".");
        System.getProperties().put("hadoop.home.dir", workaround.getAbsolutePath()); //avoids *null*\bin\winutils.exe
    }
}
