package com.example.democrud01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport //Paginação na URL
public class Democrud01Application {

	public static void main(String[] args) {
		SpringApplication.run(Democrud01Application.class, args);
	}
}
