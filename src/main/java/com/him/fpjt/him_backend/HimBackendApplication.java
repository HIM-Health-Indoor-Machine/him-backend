package com.him.fpjt.him_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScans({
		@MapperScan("com.him.fpjt.him_backend.exercise.dao"),
		@MapperScan("com.him.fpjt.him_backend.user.dao"),
		@MapperScan("com.him.fpjt.him_backend.auth.dao")
})
public class HimBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HimBackendApplication.class, args);
	}

}
