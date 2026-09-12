package com.biglibrary.order_service.clients;

import com.biglibrary.order_service.dto.ProductDTO;
import com.biglibrary.order_service.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@Slf4j
public class ProductServiceClient {

	private final RestClient client;
	private final ObjectMapper objectMapper;

	public ProductServiceClient(@Qualifier("restClient") RestClient client,
			@Qualifier("objectMapper") ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	@CircuitBreaker(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
	@Retry(name = "catalog-service")
	public Optional<ApiResponse> getProductByCode(String code) {
		log.info("Fetching product details for code: {}", code);

		var apiResponse = client.get().uri("/api/products/code/{code}", code).retrieve().body(ApiResponse.class);

		return Optional.ofNullable(apiResponse);
	}

	Optional<ApiResponse> getProductByCodeFallback(String code, Exception e) {
		System.out.println("Fallback code : " + code);
		return Optional.empty();
	}

	public Optional<ProductDTO> extractProductDTO(Optional<ApiResponse> response) {
		return response.map(ApiResponse::getData).map(data -> objectMapper.convertValue(data, ProductDTO.class));
	}

}
