package com.ssafy.live_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class LiveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveServiceApplication.class, args);
	}

}
