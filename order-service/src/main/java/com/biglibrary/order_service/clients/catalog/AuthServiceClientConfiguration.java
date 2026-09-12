package com.biglibrary.order_service.clients.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AuthServiceClientConfiguration {

	@Value("${auth-service.url}")
	private String authServiceUrl;

	@Bean
	RestClient restClientAuth() {
		return RestClient.builder().baseUrl(authServiceUrl).build();
	}

	@Bean
	ObjectMapper objectMapperAuth() {
		return new ObjectMapper();
	}

}
