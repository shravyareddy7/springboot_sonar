package com.spring_boot_final_assginment.security_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Objects;

@SpringBootApplication
@EnableDiscoveryClient
public class SecurityServiceApplication {

	public static void main(String[] args) {
		Dotenv dotenv=Dotenv.load();
		System.setProperty("SPRING_DATASOURCE_USERNAME", Objects.requireNonNull(dotenv.get("SPRING_DATASOURCE_USERNAME")));
		System.setProperty("SPRING_DATASOURCE_PASSWORD", Objects.requireNonNull(dotenv.get("SPRING_DATASOURCE_PASSWORD")));
		System.setProperty("JWT_SECRET_KEY", Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")));

		SpringApplication.run(SecurityServiceApplication.class, args);
	}

}
