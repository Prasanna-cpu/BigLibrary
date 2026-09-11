package com.biglibrary.order_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order")
public record ApplicationProperties(String catalogServiceUrl, String orderEventsExchange, String newOrdersQueue,
		String deliveredOrdersQueue, String cancelledOrdersQueue, String errorOrdersQueue) {
}