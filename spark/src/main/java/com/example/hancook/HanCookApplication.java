package com.example.hancook;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class HanCookApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanCookApplication.class, args);
	}

}


