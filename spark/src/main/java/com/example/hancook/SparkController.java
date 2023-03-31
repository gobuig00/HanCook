package com.example.hancook;


import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SparkController {

    @Scheduled(cron = "00 00 06 * * *")
    public void processFiles() throws Exception {
        long beforeTime = System.currentTimeMillis();

        //db 설정
        Map<String, String> jdbcOptions = new HashMap<>();
        jdbcOptions.put("url", "jdbc:mysql://172.26.13.247:3306/hancookdb?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true");

        jdbcOptions.put("user", "ssafy");
        jdbcOptions.put("password", "ssafy");
        jdbcOptions.put("dbtable", "deal");
        jdbcOptions.put("driver", "com.mysql.cj.jdbc.Driver");

        SparkSession spark = SparkSession.builder()
                .appName("hancook")
                .master("local")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();

        String fileDirectory = "./data/agri";
        File[] csvFiles = new File(fileDirectory).listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".csv"));

        if (csvFiles != null) {
            for (File file : csvFiles) {
                String fileName= file.getName();
                System.out.println("Processing: " + file.getName());

                JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());

                JavaRDD<String> inputRdd = sc.textFile("./data/agri/"+fileName);
                StructType schema = new StructType()
                        .add("deal_date", DataTypes.StringType)
                        .add("large", DataTypes.StringType)
                        .add("medium", DataTypes.StringType)
                        .add("small", DataTypes.StringType)
                        .add("origin", DataTypes.StringType)
                        .add("isKg", DataTypes.DoubleType)
                        .add("price", DataTypes.DoubleType);

                JavaRDD<Row> sizeRdd = inputRdd.map(line -> {
                    String[] parts = line.split(",");
                    if (parts.length == 7 && parts[5].trim().equals("0")) {
                        return RowFactory.create("20" + parts[0].replaceAll("[^0-9]", "").trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim(),
                                parts[4].trim().equals("0") ? "origin" : "korea",
                                Double.parseDouble(parts[5].trim()),
                                Double.parseDouble(parts[6].trim()));
                    }
                    return null;
                }).filter(row -> row != null);

                JavaRDD<Row> sizeNRdd = inputRdd.map(line -> {
                    String[] parts = line.split(",");
                    if (parts.length != 7 && parts.length != 6 && parts[parts.length - 2].trim().equals("0")) {
                        return RowFactory.create("20" + parts[0].replaceAll("[^0-9]", "").trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[parts.length - 4].trim(),
                                parts[parts.length - 3].trim().equals("0") ? "origin" : "korea",
                                Double.parseDouble(parts[parts.length - 2].trim()),
                                Double.parseDouble(parts[parts.length - 1].trim()));
                    }
                    return null;
                }).filter(row -> row != null);

                JavaRDD<Row> unRdd = sizeRdd.union(sizeNRdd);
                Dataset<Row> df = spark.createDataFrame(unRdd, schema);

                df.createOrReplaceTempView("table");
                Dataset<Row> v1 = spark.sql("SELECT deal_date, large, medium, small, origin, AVG(price) as avgPrice FROM table GROUP BY deal_date, large, medium, small, origin");
                v1.createOrReplaceTempView("table2");

                Dataset<Row> v2 = spark.sql("SELECT table.deal_date, table.large, table.medium, table.small, table.origin, table.price, table2.avgPrice " + "FROM table INNER JOIN table2 " +
                        "ON table.deal_date = table2.deal_date " +
                        "AND table.large = table2.large " +
                        "AND table.medium = table2.medium " +
                        "AND table.small = table2.small " +
                        "AND table.origin = table2.origin");
                v2.createOrReplaceTempView("table3");

                Dataset<Row> v3 = spark.sql("SELECT * FROM table3 WHERE avgPrice * 1.5 > price AND avgPrice * 0.5 < price");
                v3.createOrReplaceTempView("table4");

                Dataset<Row> v4 = spark.sql("SELECT deal_date, large, medium, small, origin, MAX(price) as max, MIN(price) as min, avgPrice as price " +
                        "FROM table4 GROUP BY deal_date, large, medium, small, origin, avgPrice");

                Dataset<Row> df2 = v4.toDF();
                df2.show();
                df2.write()
                        .mode(SaveMode.Append)
                        .format("jdbc")
                        .options(jdbcOptions)
                        .save();



                long afterTime = System.currentTimeMillis();
                long difTime = afterTime - beforeTime;
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println("Run Time : " + difTime + "ms");
                System.out.println("-----------------------------------------------------------------------------------");

            }
        }
//        육류 데이터
        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
        JavaRDD<String> inputBeefRdd = sc.textFile("./data/beef/beef.csv");
        StructType schema = new StructType()	//deal_date,large,medium,소,origin,income,가격
                .add("deal_date", DataTypes.StringType)
                .add("large", DataTypes.StringType)
                .add("medium", DataTypes.StringType)
                .add("small", DataTypes.StringType)
                .add("origin", DataTypes.StringType)
                .add("max", DataTypes.DoubleType)
                .add("min", DataTypes.DoubleType)
                .add("price",DataTypes.DoubleType);
        JavaRDD<Row> beefPar = inputBeefRdd.map(line -> {
            String[] parts = line.split(",");
            if (parts.length == 8) {
                return RowFactory.create(parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        Double.parseDouble(parts[5].trim()),
                        Double.parseDouble(parts[6].trim()),
                        Double.parseDouble(parts[7].trim()));
            }
            return null;
        }).filter(row -> row != null);
        Dataset<Row> beefDf = spark.createDataFrame(beefPar, schema);
        beefDf.show();
        beefDf.write()
                .mode(SaveMode.Append)
                .format("jdbc")
                .options(jdbcOptions)
                .save();

        //마트 데이터 삽입
        jdbcOptions.put("dbtable", "mart");
        sc = new JavaSparkContext(spark.sparkContext());
        JavaRDD<String> inputMartRdd = sc.textFile("./data/mart/ingre.csv");
        schema = new StructType()	//deal_date,large,medium,소,origin,income,가격
                .add("ingredient_name", DataTypes.StringType)
                .add("item_name", DataTypes.StringType)
                .add("item_no", DataTypes.IntegerType)
                .add("item_price", DataTypes.StringType)
                .add("item_url", DataTypes.StringType)
                .add("mart", DataTypes.IntegerType)
                .add("ingredient_id", DataTypes.LongType);
        JavaRDD<Row> martPar = inputMartRdd.map(line -> {
            String[] parts = line.split(",");
            if (parts.length == 7) {
                return RowFactory.create(parts[0].trim(),
                        parts[1].trim(),
                        Integer.parseInt(parts[2].trim()),
                        parts[3].trim(),
                        parts[4].trim(),
                        Integer.parseInt(parts[5].trim()),
                        Long.parseLong(parts[6].trim()));
            }
            return null;
        }).filter(row -> row != null);
        Dataset<Row> martDf = spark.createDataFrame(martPar, schema);
        martDf.show();
        martDf.write()
                .mode(SaveMode.Append)
                .format("jdbc")
                .options(jdbcOptions)
                .save();
    }
}
