package com.biglibrary.order_service.publisher;

import com.biglibrary.order_service.ApplicationProperties;
import com.biglibrary.order_service.models.embedded_models.OrderCancelledEvent;
import com.biglibrary.order_service.models.embedded_models.OrderCreatedEvent;
import com.biglibrary.order_service.models.embedded_models.OrderDeliveredEvent;
import com.biglibrary.order_service.models.embedded_models.OrderErrorEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
	private final RabbitTemplate rabbitTemplate;
	private final ApplicationProperties properties;

	private void send(String routingKey, Object payload) {
		rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, payload);
	}

	public void publish(OrderCreatedEvent event) {
		send(properties.newOrdersQueue(), event);
	}

	public void publish(OrderDeliveredEvent event) {
		send(properties.deliveredOrdersQueue(), event);
	}

	public void publish(OrderCancelledEvent event) {
		send(properties.cancelledOrdersQueue(), event);
	}

	public void publish(OrderErrorEvent event) {
		send(properties.errorOrdersQueue(), event);
	}

}
