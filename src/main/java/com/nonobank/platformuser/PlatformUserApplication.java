package com.nonobank.platformuser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
@EnableAutoConfiguration
// @EnableRedisHttpSession
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
@RestController
@RefreshScope
public class PlatformUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlatformUserApplication.class, args);
	}

	@Value("${server.port}")
	String port;
	
	@RequestMapping("/hello")
	public String home() {
		return "hello world from port ";
	}
}
