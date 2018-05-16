package com.nonobank.platformuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
@EnableAutoConfiguration
//@EnableRedisHttpSession
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class PlatformUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlatformUserApplication.class, args);
	}
}
