package com.example.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RpcApplication {
	public static void main(String[] args) {
		SpringApplication.run(RpcApplication.class, args);
	}
}
