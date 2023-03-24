package com.example.hancook;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CsvToMysql {

    public static void main(String[] args) {
        // Spark 설정 초기화
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("CsvToMysql");
        JavaSparkContext sc = new JavaSparkContext(conf);


        //        SparkSession spark = SparkSession.builder().master("local").appName("spark").config("spark.some.config.option", "some-vaule").getOrCreate();


        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        // MySQL 접속 정보 설정
        Map<String, String> jdbcOptions = new HashMap<>();
        jdbcOptions.put("url", "jdbc:mysql://localhost:3306/hancookdb?useSSL=true");
        jdbcOptions.put("user", "ssafy");
        jdbcOptions.put("password", "ssafy");
        jdbcOptions.put("dbtable", "deal");
        jdbcOptions.put("driver", "com.mysql.cj.jdbc.Driver");

        // 파일 경로
        String fileDirectory = "src/main/datas";

        File[] csvFiles = new File(fileDirectory).listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".csv"));

        System.out.println(csvFiles.length);
        if (csvFiles != null) {
            for (File file : csvFiles) {
                System.out.println("Processing: " + file.getName());
                processCsvFile(spark, file.getPath(), jdbcOptions);
            }
        }


        // CSV 파일 처리
//        File[] directories = new File(fileDirectory).listFiles(File::isDirectory);
//        File[] csvFiles = new File(fileDirectory).listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".csv"));
//
//
//        System.out.println(csvFiles.length);
//        if (csvFiles != null) {
//            for (File directory : csvFiles) {
//                File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
//                if (files != null) {
//                    for (File file : files) {
//                        System.out.println("llllll");
//                        processCsvFile(spark, file.getPath(), jdbcOptions);
//                    }
//                }
//            }
        }


    private static void processCsvFile(SparkSession spark, String filePath, Map<String, String> jdbcOptions) {
        // CSV 파일 읽기

        StructType schema = new StructType()
                        .add("date", DataTypes.StringType, true)
                        .add("large", DataTypes.StringType, true)
                        .add("middle", DataTypes.StringType, true)
                        .add("small", DataTypes.StringType, true)
                        .add("isIncome", DataTypes.StringType, true)
                        .add("isKg", DataTypes.StringType, true)
                        .add("price", DataTypes.StringType, true);
//Dataset<Row> df = spark.read().schema(schema).csv(input);
        Dataset<Row> csvData = spark.read()
                .format("csv")
                .option("header", "true")
                .option("inferSchema", "true")
                .load(filePath);

        // MySQL 데이터베이스에 저장
        csvData.write()
                .mode(SaveMode.Append)
                .format("jdbc")
                .options(jdbcOptions)
                .save();
    }
}