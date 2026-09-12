package com.biglibrary.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImplementation")
public class AuthServiceApplication {

	public static void main(String[] args) {

		System.out.println("Auth Service Started");

		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
