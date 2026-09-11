package com.biglibrary.order_service.controller;

import com.biglibrary.order_service.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

class OrderControllerTest extends AbstractIT {

	@BeforeEach
	public void setUp() {
		super.setUp();
	}

	@Nested
	class CreateOrderTests {

		@Test
		void shouldCreateOrderSuccessfully() {

			String requestBody = """
					{
					    "userName": "satyajit",
					    "customer": {
					        "name": "Satyajit Deshmukh",
					        "email": "satya891@gmail.com",
					        "phone": "9772891911"
					    },
					    "address": {
					        "addressLine1": "2nd Block",
					        "addressLine2": "Pimpri",
					        "city": "Pune",
					        "state": "Maharashtra",
					        "country": "India",
					        "zipCode": "600061"
					    },
					    "orderItems": [
					        {
					            "code": "P206",
					            "name": "IPhone 17 Pro",
					            "price": 120000,
					            "quantity": 1
					        }
					    ]
					}
					""";

			given().contentType(ContentType.JSON).body(requestBody).when().post("/api/orders/create").then()
					.statusCode(201).body("orderNumber", notNullValue());
		}

	}
}