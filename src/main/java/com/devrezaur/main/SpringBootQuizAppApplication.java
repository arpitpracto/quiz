package com.devrezaur.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.devrezaur.main")  // explicitly define base package for scanning
@EnableAutoConfiguration
public class SpringBootQuizAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootQuizAppApplication.class, args);
	}
}
