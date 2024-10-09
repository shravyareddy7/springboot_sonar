package com.springboot_final_assignment.comment_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Objects;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CommentServiceApplication {

	public static void main(String[] args) {
			SpringApplication.run(CommentServiceApplication.class, args);
	}

}
