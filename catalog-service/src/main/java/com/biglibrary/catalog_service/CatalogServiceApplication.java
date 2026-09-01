package com.biglibrary.catalog_service;

import org.hibernate.internal.log.SubSystemLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogServiceApplication {

	public static void main(String[] args) {
		System.setProperty("user.timezone", "Asia/Kolkata");
		System.out.println("Welcome");
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

}
