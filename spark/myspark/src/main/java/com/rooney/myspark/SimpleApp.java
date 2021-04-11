package com.rooney.myspark;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;

public class SimpleApp {
  public static void main(String[] args) {
    String logFile = "/Users/paul/Dev/Apps/spark-3.1.1-bin-hadoop3.2/README.md"; // Should be some file on your system
    //SparkSession spark = SparkSession.builder().appName("Simple Application").getOrCreate();
    SparkSession spark = SparkSession.builder().appName("Simple Application").master("local[*]").getOrCreate();

    Dataset<String> logData = spark.read().textFile(logFile).cache();

    //not working in java due to ambiguous filter method
    //See https://stackoverflow.com/questions/59712047/java8-maven-raise-error-reference-to-filter-is-ambiguous
    //long numAs = logData.filter(s -> s.contains("a")).count();
    //long numBs = logData.filter(s -> s.contains("b")).count();

    long numAs = logData.filter((org.apache.spark.api.java.function.FilterFunction<String>)s -> s.contains("a")).count();
    long numBs = logData.filter((org.apache.spark.api.java.function.FilterFunction<String>)s -> s.contains("b")).count();


    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);



    spark.stop();
  }

}