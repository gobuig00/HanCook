package com.example.hancook;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HanCookApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HanCookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		long beforeTime = System.currentTimeMillis();

		SparkSession spark = SparkSession.builder()
				.appName("VeggieMeal")
				.master("local")
				.config("spark.some.config.option", "some-value")
				.getOrCreate();

		JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
//        JavaRDD<String> inputRdd = sc.textFile(args[0]);
		JavaRDD<String> inputRdd = sc.textFile("src/main/datas/test.csv");
		StructType schema = new StructType()
				.add("date", DataTypes.StringType)
				.add("large", DataTypes.StringType)
				.add("middle", DataTypes.StringType)
				.add("small", DataTypes.StringType)
				.add("isIncome", DataTypes.StringType)
				.add("isKg", DataTypes.DoubleType)
				.add("price", DataTypes.DoubleType);

		JavaRDD<Row> sizeRdd = inputRdd.map(line -> {
			String[] parts = line.split(",");
			if (parts.length == 7 && parts[5].trim().equals("0")) {
				return RowFactory.create("20" + parts[0].replaceAll("[^0-9]", "").trim(),
						parts[1].trim(),
						parts[2].trim(),
						parts[3].trim(),
						parts[4].trim().equals("0") ? "income" : "korea",
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
						parts[parts.length - 3].trim().equals("0") ? "income" : "korea",
						Double.parseDouble(parts[parts.length - 2].trim()),
						Double.parseDouble(parts[parts.length - 1].trim()));
			}
			return null;
		}).filter(row -> row != null);

		JavaRDD<Row> unRdd = sizeRdd.union(sizeNRdd);
		Dataset<Row> df = spark.createDataFrame(unRdd, schema);

		df.createOrReplaceTempView("table");
		Dataset<Row> v1 = spark.sql("SELECT date, large, middle, small, isIncome, AVG(price) as avgPrice FROM table GROUP BY date, large, middle, small, isIncome");
		v1.createOrReplaceTempView("table2");

		Dataset<Row> v2 = spark.sql("SELECT table.date, table.large, table.middle, table.small, table.isIncome, table.price, table2.avgPrice " +    "FROM table INNER JOIN table2 " +
				"ON table.date = table2.date " +
				"AND table.large = table2.large " +
				"AND table.middle = table2.middle " +
				"AND table.small = table2.small " +
				"AND table.isIncome = table2.isIncome");
		v2.createOrReplaceTempView("table3");

		Dataset<Row> v3 = spark.sql("SELECT * FROM table3 WHERE avgPrice * 1.5 > price AND avgPrice * 0.5 < price");
		v3.createOrReplaceTempView("table4");

		Dataset<Row> v4 = spark.sql("SELECT date, large, middle, small, isIncome, MAX(price) as maxPrice, MIN(price) as minPrice, avgPrice " +
				"FROM table4 GROUP BY date, large, middle, small, isIncome, avgPrice");
		Dataset<Row> df2 = v4.toDF();

		df2.write().csv(args[1]);

		long afterTime = System.currentTimeMillis();
		long difTime = afterTime - beforeTime;
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("Run Time : " + difTime + "ms");
		System.out.println("-----------------------------------------------------------------------------------");
	}

}