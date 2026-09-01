package com.biglibrary.catalog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogServiceApplication {

	public static void main(String[] args) {
		System.setProperty("user.timezone", "Asia/Kolkata");
		System.out.println("Welcome to Catalog Service Application");
		System.out.println("Running on port 8081");
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

}
