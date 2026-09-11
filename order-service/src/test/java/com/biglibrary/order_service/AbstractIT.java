package com.biglibrary.order_service;

import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractIT {

	@LocalServerPort
	int port;

	static WireMockContainer wireMockServer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

	@BeforeAll
	static void beforeAll() {
		wireMockServer.start();
		RestAssured.baseURI = "http://" + wireMockServer.getHost();
		RestAssured.port = wireMockServer.getPort();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("orders.catalog-service-url", wireMockServer::getBaseUrl);
	}

	protected void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
}
