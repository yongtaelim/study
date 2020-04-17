package com.example.autoconfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootConfiguration
//@ComponentScan(value = "com.example.autoconfiguration")
//@EnableAutoConfiguration
// or
@SpringBootApplication
public class AutoconfigurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoconfigurationApplication.class, args);
	}

}
