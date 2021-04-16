package com.rooney.myspark;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.LongType;
import org.apache.spark.sql.types.StringType;
import scala.Tuple2;

import java.util.Date;

import static org.apache.spark.sql.functions.*;


public class Parquet1 {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("Parquet1 Application").master("local[*,3]").getOrCreate(); //3 is retries
        Parquet1 parquet1 = new Parquet1();
        //jsonToParquetAndSql(spark);

        parquet1.iterate(spark);


    }

    /*
    +------+--------------+----------------+
    |  name|favorite_color|favorite_numbers|
    +------+--------------+----------------+
    |Alyssa|          null|  [3, 9, 15, 20]|
    |   Ben|           red|              []|
    +------+--------------+----------------+
    */
    private void iterate(SparkSession spark) {

        //Dataset<Row> pqDs = spark.read().parquet("/Users/paul/Dev/Apps/spark-3.1.1-bin-hadoop3.2/examples/src/main/resources/users.parquet");
        Dataset<Row> df = spark.read().json("/Users/paul/Dev/Code/Repo1/spark/myspark/src/main/resources/people.json");

        try {
            doIterateCols(df);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Fin");
        }

    }

    private void doIterateRows(Dataset<Row> df) {

    }

    private void doIterateCols(Dataset<Row> df) {
        // I think this will work with UDFs for each datatype. or maybe highlevel ones like numeric vs string
        // I think this is the more correct way of coding it.
        // iterating over rows feels less optimal
        //spark feels like it doesn't want to do this... see https://delta.io instead

        //df.show();
        //iterate on rows
        //df.foreach((ForeachFunction<Row>) row -> System.out.println(row.toString()));

        //See instance of datafame
        //System.out.println(df.rdd().id());

        //df.withColumn("name", df.col("name") + " updated");

        //import static org.apache.spark.sql.functions.*;
        //WORKS df.withColumn("name", lit(col("name") + " - updated") ).show();
        //TODO have when for each type or lambda? or UDF


        // iterate over col names and get info on col obj
//        Arrays.asList(df.columns()).forEach(c -> {
//            System.out.println(c);
//            Column col = df.col(c); //FAIL as lambda can't update outside var
//            System.out.println(col.expr().dataType());
//        });

        //try Polymorphism
//        Arrays.asList(df.columns()).forEach(c -> {
//            System.out.println(c);
//            df = df.withColumn("name", lit(col("name")) ); //FAIL as lambda can't update outside var
//        });

//        for (String colName: df.columns()) {
//            System.out.println(colName);
//            df = df.withColumn(colName, lit(obfuscateCol(df.col(colName))) );
//        }
        //end try Polymorphism

        //try UDF
        df.sqlContext().udf().register("obfuscateStringUDF", obfuscateStringUDF(), DataTypes.StringType);
        df.sqlContext().udf().register("obfuscateLongUDF", obfuscateLongUDF(), DataTypes.LongType);

        for(Tuple2<String, String> colNameAndType: df.dtypes()) {
            String colName = colNameAndType._1;
            String colType = colNameAndType._2;

            switch (colType) {
                case "StringType": df = df.withColumn(colName, callUDF("obfuscateStringUDF", col(colName))); break;
                case "LongType"  : df = df.withColumn(colName, callUDF("obfuscateLongUDF", col(colName))); break;
                default: System.out.println("not supported: " + colType);
            }


//            df = df.withColumn(colName,
//                    when(col(colName).xx_cant get conditional col to work with typexx, df = df.withColumn(colName).
//                    when(col(colName).isNull(), functions.callUDF("obfuscateLongUDF", df.col(colName).
//                    otherwise(df.col(colName) ))))));
        }

        df.show();

        //endtry UDF

        //TODO could we use df.map?
        //df.map(value -> )
        //END could we use df.map?


        //somehow use dtypes
//        Tuple2<String, String>[] dtypes = df.dtypes(); //Returns all column names and their data types as an array.
//        for(Tuple2<String, String> colNameAndType: dtypes) {
//            String colName = colNameAndType._1;
//            String colType = colNameAndType._2;
//            System.out.println("colName="+colName + ", colType=" + colType);
//        }

        //

        //UDF eg
        //df.sqlContext().udf().register("normalizeDateValue", stringToDate(), DataTypes.DateType );
        //df = df.withColumn(column, functions.callUDF("normalizeDateValue", df.col(column)));

        //could perform sql update
        //parquetFileDF.createOrReplaceTempView("parquetFile");
        //Dataset<Row> namesDF = spark.sql("SELECT name FROM parquetFile WHERE age BETWEEN 13 AND 19");

        //could gen new DF using functions on cols
        //df.select(col("name"), col("age").plus(1)).show();

        //compare cols
        //when(col("word") === lit("china"), lit("asia"))

        System.out.println("fin");
    }

    private UDF1<String, String> obfuscateStringUDF() {
        return ( s1 ) -> {
            if (null==s1) {
                return null;
            }
           return s1 + " updated";
        };
    }

    private UDF1<Long, Long> obfuscateLongUDF() {
        return ( l1 ) -> {
            if (null==l1) {
                return null;
            }
            return l1 + 10000L;
        };
    }

    public Column obfuscateCol(Column col) {
        System.out.println("colName="+col.expr().toString() + ", type="+col.expr().dataType());


        col = obfuscateColType(col.expr().dataType(), col);

        return col;
    }

    Column obfuscateColType(DataType  type, Column col) {
        return lit(col + " - updated");
    }

    Column obfuscateColType(LongType type, Column col) {
        //FAIL: I think we need a UDF to get at the value
        return col; //lit(col + 9999L);
    }

    Column obfuscateColType(StringType type, Column col) {
        return lit(col + " - updated");
    }

    private UDF1<String, Date> stringToDate() {
        return ( s1 ) -> {
            if (null==s1) {
                return null;
            }
            if (s1 instanceof String) {
                //TODO: parse s1 into Date
                return new java.util.Date();
            }

            return null;
        };
    }

    private static void jsonToParquetAndSql(SparkSession spark) {
        Dataset<Row> peopleDF = spark.read().json("/Users/paul/Dev/Apps/spark-3.1.1-bin-hadoop3.2/examples/src/main/resources/people.json");

        // DataFrames can be saved as Parquet files, maintaining the schema information
        peopleDF.write().mode("overwrite").parquet("people.parquet");

        // Read in the Parquet file created above.
        // Parquet files are self-describing so the schema is preserved
        // The result of loading a parquet file is also a DataFrame
        Dataset<Row> parquetFileDF = spark.read().parquet("people.parquet");

        // Parquet files can also be used to create a temporary view and then used in SQL statements
        parquetFileDF.createOrReplaceTempView("parquetFile");
        Dataset<Row> namesDF = spark.sql("SELECT name FROM parquetFile WHERE age BETWEEN 13 AND 19");
        Dataset<String> namesDS = namesDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
                Encoders.STRING());
        namesDS.show();
    }
}
