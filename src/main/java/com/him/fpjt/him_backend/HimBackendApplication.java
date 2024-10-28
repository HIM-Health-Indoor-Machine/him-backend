package com.him.fpjt.him_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.him.fpjt.him_backend.exercise.dao")
public class HimBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HimBackendApplication.class, args);
	}

}
