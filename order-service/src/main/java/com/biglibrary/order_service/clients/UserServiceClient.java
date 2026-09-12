package com.biglibrary.order_service.clients;

import com.biglibrary.order_service.dto.UserDTO;
import com.biglibrary.order_service.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@Slf4j
public class UserServiceClient {

	private final RestClient client;
	private final ObjectMapper objectMapper;

	public UserServiceClient(@Qualifier("restClientAuth") RestClient client,
			@Qualifier("objectMapperAuth") ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	@CircuitBreaker(name = "auth-service", fallbackMethod = "getUserByTokenHandlerFallback")
	@Retry(name = "catalog-service")
	public Optional<ApiResponse> getUserByTokenHandler(String jwt) {
		String authorizationHeader = jwt.startsWith("Bearer ") ? jwt : "Bearer " + jwt;
		var apiResponse = client.get().uri("/api/users/me")
				.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader)).retrieve()
				.body(ApiResponse.class);
		log.info("apiResponse of User : {}", apiResponse);
		return Optional.ofNullable(apiResponse);
	}

	Optional<ApiResponse> getUserByTokenHandlerFallback(String jwt, Exception e) {
		System.out.println("Exception : " + e.getMessage());
		return Optional.empty();
	}

	public Optional<UserDTO> extractUserDTO(Optional<ApiResponse> response) {
		return response.map(ApiResponse::getData).map(data -> objectMapper.convertValue(data, UserDTO.class));
	}

}
