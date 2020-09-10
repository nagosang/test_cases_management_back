package com.testCaseManagement.tcm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.testCaseManagement.tcm.mapper")
public class TcmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcmApplication.class, args);
	}

}
