package com.biglibrary.order_service.clients.catalog;

import com.biglibrary.order_service.ApplicationProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CatalogServiceClientConfiguration {

	@Bean
	RestClient restClient(ApplicationProperties properties) {
		return RestClient.builder().baseUrl(properties.catalogServiceUrl()).build();
	}

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
