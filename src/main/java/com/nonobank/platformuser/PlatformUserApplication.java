package com.nonobank.platformuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
//@EnableRedisHttpSession
public class PlatformUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlatformUserApplication.class, args);
	}
}
