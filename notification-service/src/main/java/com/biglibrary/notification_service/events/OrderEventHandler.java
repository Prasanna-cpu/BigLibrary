package com.biglibrary.notification_service.events;

import com.biglibrary.notification_service.entity.OrderEvents;
import com.biglibrary.notification_service.entity.embedded_models.OrderCancelledEvent;
import com.biglibrary.notification_service.entity.embedded_models.OrderCreatedEvent;
import com.biglibrary.notification_service.entity.embedded_models.OrderDeliveredEvent;
import com.biglibrary.notification_service.entity.embedded_models.OrderErrorEvent;
import com.biglibrary.notification_service.services.NotificationService;
import com.biglibrary.notification_service.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandler {

	private final NotificationService notificationService;
	private final OrderEventRepository orderEventRepository;

	@RabbitListener(queues = "${notifications.new-orders-queue}")
	void handleOrderCreatedEvent(OrderCreatedEvent event) {
		log.info("Received OrderCreatedEvent for order number: {}", event.orderNumber());
		if (orderEventRepository.existsByEventId(event.eventId())) {
			log.warn("Duplicate OrderCreatedEvent received for order number: {} with eventId: {}", event.orderNumber(),
					event.eventId());
			return;
		}

		notificationService.sendOrderCreatedNotification(event);
		OrderEvents orderEvents = new OrderEvents(event.eventId());
		orderEventRepository.save(orderEvents);
	}

	@RabbitListener(queues = "${notifications.delivered-orders-queue}")
	void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
		log.info("Received OrderDeliveredEvent for order number: {}", event.orderNumber());
		if (orderEventRepository.existsByEventId(event.eventId())) {
			log.warn("Duplicate OrderDeliveredEvent received for order number: {} with eventId: {}",
					event.orderNumber(), event.eventId());
			return;
		}
		notificationService.sendOrderDeliveredNotification(event);
		OrderEvents orderEvents = new OrderEvents(event.eventId());
		orderEventRepository.save(orderEvents);
	}

	@RabbitListener(queues = "${notifications.cancelled-orders-queue}")
	void handleOrderCancelledEvent(OrderCancelledEvent event) {
		log.info("Received OrderCancelledEvent for order number: {}", event.orderNumber());
		if (orderEventRepository.existsByEventId(event.eventId())) {
			log.warn("Duplicate OrderCancelledEvent received for order number: {} with eventId: {}",
					event.orderNumber(), event.eventId());
			return;
		}
		notificationService.sendOrderCancelledNotification(event);
		OrderEvents orderEvents = new OrderEvents(event.eventId());
		orderEventRepository.save(orderEvents);
	}

	@RabbitListener(queues = "${notifications.error-orders-queue}")
	void handleOrderErrorEvent(OrderErrorEvent event) {
		log.info("Received OrderErrorEvent for order number: {}", event.orderNumber());
		if (orderEventRepository.existsByEventId(event.eventId())) {
			log.warn("Duplicate OrderErrorEvent received for order number: {} with eventId: {}", event.orderNumber(),
					event.eventId());
			return;
		}
		notificationService.sendOrderErrorEventNotification(event);
		OrderEvents orderEvents = new OrderEvents(event.eventId());
		orderEventRepository.save(orderEvents);
	}

}
