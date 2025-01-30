package com.skillio.api_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.skillio.api_v1")
public class ApiV1Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiV1Application.class, args);
	}

}
