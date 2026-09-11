package com.biglibrary.order_service.controller;

import com.biglibrary.order_service.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rabbitmq")
public class RabbitMQDemoController {
	private final RabbitTemplate rabbitTemplate;
	private final ApplicationProperties properties;

	record MyPayload(String content) {
	}

	record MyMessage(String routingKey, MyPayload payload) {
	}

	@PostMapping("/send")
	public void sendMessage(@RequestBody MyMessage message) {
		rabbitTemplate.convertAndSend(properties.orderEventsExchange(), message.routingKey(), message.payload());
		log.info("Message Sent : " + message);
	}
}
