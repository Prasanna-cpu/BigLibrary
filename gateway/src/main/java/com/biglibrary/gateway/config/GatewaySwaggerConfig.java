package com.biglibrary.gateway.config;

import jakarta.annotation.PostConstruct;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

@Configuration
public class GatewaySwaggerConfig {
	private final RouteDefinitionLocator routeDefinitionLocator;
	private final SwaggerUiConfigProperties properties;

	public GatewaySwaggerConfig(RouteDefinitionLocator routeDefinitionLocator, SwaggerUiConfigProperties properties) {
		this.routeDefinitionLocator = routeDefinitionLocator;
		this.properties = properties;
	}

	@PostConstruct
	public void init() {
		List<RouteDefinition> definitions = routeDefinitionLocator.getRouteDefinitions().collectList().block();
		Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
		assert definitions != null;
		definitions.stream().filter(routeDefinition -> {
			assert routeDefinition.getId() != null;
			return routeDefinition.getId().matches(".*-service");
		}).forEach(routeDefinition -> {
			String name = routeDefinition.getId().replaceAll("-service", "");
			AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl(
					name, DEFAULT_API_DOCS_URL + "/" + name, null);
			urls.add(swaggerUrl);
		});
		properties.setUrls(urls);
	}

}
