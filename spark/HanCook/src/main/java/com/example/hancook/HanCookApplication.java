package com.example.hancook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HanCookApplication {

	public static void main(String[] args) {

		SpringApplication.run(HanCookApplication.class, args);

		// CsvToMysql 클래스 실행
		CsvToMysql.main(args);


	}

}
