package com.biglibrary.catalog_service.repository;

import com.biglibrary.catalog_service.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ProductRepositoryTest {

	@Container
	static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
	}

	@Autowired
	private ProductRepository productRepository;

	@Test
	void shouldGetAllProducts() {
		List<Product> products = productRepository.findAll();
		assertEquals(10, products.size());
	}

	@Test
	void shouldGetProductByCode() {
		Product product = productRepository.findByCode("BK001")
				.orElseThrow(() -> new RuntimeException("Product not found"));
		assertEquals("BK001", product.getCode());
	}

}