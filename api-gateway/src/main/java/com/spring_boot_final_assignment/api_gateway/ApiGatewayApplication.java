package com.spring_boot_final_assignment.api_gateway;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Objects;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {

		Dotenv dotenv=Dotenv.load();
		System.setProperty("JWT_SECRET_KEY", Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")));
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
