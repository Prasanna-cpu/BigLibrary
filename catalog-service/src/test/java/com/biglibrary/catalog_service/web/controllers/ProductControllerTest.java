package com.biglibrary.catalog_service.web.controllers;

import com.biglibrary.catalog_service.AbstractIT;
import com.biglibrary.catalog_service.dto.ProductDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest extends AbstractIT {

	@Test
	void shouldReturnProducts() {
		given().contentType(ContentType.JSON).when().get("/api/products/all").then().statusCode(200)
				.body("data.data", hasSize(10)).body("data.totalElements", is(10)).body("data.pageNumber", is(1))
				.body("data.totalPages", is(1)).body("data.isFirst", is(true)).body("data.isLast", is(true))
				.body("data.hasNext", is(false)).body("data.hasPrevious", is(false));
	}

	@Test
	void shouldGetProductByCode() {
		ProductDTO product = given().contentType(ContentType.JSON).when().get("/api/products/code/{code}", "BK001")
				.then().statusCode(200).extract().jsonPath().getObject("data", ProductDTO.class);

		assertThat(product.getCode(), equalTo("BK001"));
		assertThat(product.getName(), equalTo("The Great Gatsby"));
		assertThat(product.getDescription(), equalTo("A classic novel about the American Dream"));
		assertThat(product.getPrice(), equalTo(new BigDecimal("15.99")));
	}

}