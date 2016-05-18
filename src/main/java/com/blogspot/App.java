package com.blogspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
