package com.nonobank.platformuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableAutoConfiguration
public class PlatformUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformUserApplication.class, args);
	}
}
