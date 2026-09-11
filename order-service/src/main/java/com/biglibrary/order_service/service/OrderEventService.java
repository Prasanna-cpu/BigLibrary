package com.biglibrary.order_service.service;

import com.biglibrary.order_service.enums.OrderEventType;
import com.biglibrary.order_service.models.OrderEvents;
import com.biglibrary.order_service.models.embedded_models.OrderCancelledEvent;
import com.biglibrary.order_service.models.embedded_models.OrderCreatedEvent;
import com.biglibrary.order_service.models.embedded_models.OrderDeliveredEvent;
import com.biglibrary.order_service.models.embedded_models.OrderErrorEvent;
import com.biglibrary.order_service.publisher.OrderEventPublisher;
import com.biglibrary.order_service.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class OrderEventService {

	private final OrderEventPublisher orderEventPublisher;
	private final OrderEventRepository orderEventRepository;
	private final JsonMapper jsonMapper;

	private String toJsonPayload(Object object) {
		return jsonMapper.writeValueAsString(object);
	}

	private <T> T fromJsonPayload(String json, Class<T> type) {
		return jsonMapper.readValue(json, type);
	}

	void save(OrderCreatedEvent event) {
		OrderEvents orderEvents = new OrderEvents();
		orderEvents.setEventId(event.eventId());
		orderEvents.setEventType(OrderEventType.ORDER_CREATED);
		orderEvents.setPayload(toJsonPayload(event));
		orderEvents.setOrderNumber(event.orderNumber());
		orderEventRepository.save(orderEvents);
	}

	void save(OrderDeliveredEvent event) {
		OrderEvents orderEvents = new OrderEvents();
		orderEvents.setEventId(event.eventId());
		orderEvents.setEventType(OrderEventType.ORDER_DELIVERED);
		orderEvents.setPayload(toJsonPayload(event));
		orderEvents.setOrderNumber(event.orderNumber());
		orderEventRepository.save(orderEvents);
	}

	void save(OrderCancelledEvent event) {
		OrderEvents orderEvents = new OrderEvents();
		orderEvents.setEventId(event.eventId());
		orderEvents.setEventType(OrderEventType.ORDER_CANCELLED);
		orderEvents.setPayload(toJsonPayload(event));
		orderEvents.setOrderNumber(event.orderNumber());
		orderEventRepository.save(orderEvents);
	}

	void save(OrderErrorEvent event) {
		OrderEvents orderEvents = new OrderEvents();
		orderEvents.setEventId(event.eventId());
		orderEvents.setEventType(OrderEventType.ORDER_PROCESSING_FAILED);
		orderEvents.setPayload(toJsonPayload(event));
		orderEvents.setOrderNumber(event.orderNumber());
		orderEventRepository.save(orderEvents);
	}

	public void publishOrderEvents() {
		Sort sort = Sort.by("createdAt").ascending();
		List<OrderEvents> events = orderEventRepository.findAll(sort);
		log.info("Found {} Order Events to be published", events.size());
		for (OrderEvents event : events) {
			this.publishEvent(event);
			orderEventRepository.delete(event);
		}
	}

	private void publishEvent(OrderEvents event) {
		OrderEventType eventType = event.getEventType();
		switch (eventType) {
			case ORDER_CREATED :
				OrderCreatedEvent orderCreatedEvent = fromJsonPayload(event.getPayload(), OrderCreatedEvent.class);
				orderEventPublisher.publish(orderCreatedEvent);
				break;
			case ORDER_DELIVERED :
				OrderDeliveredEvent orderDeliveredEvent = fromJsonPayload(event.getPayload(),
						OrderDeliveredEvent.class);
				orderEventPublisher.publish(orderDeliveredEvent);
				break;
			case ORDER_CANCELLED :
				OrderCancelledEvent orderCancelledEvent = fromJsonPayload(event.getPayload(),
						OrderCancelledEvent.class);
				orderEventPublisher.publish(orderCancelledEvent);
				break;
			case ORDER_PROCESSING_FAILED :
				OrderErrorEvent orderErrorEvent = fromJsonPayload(event.getPayload(), OrderErrorEvent.class);
				orderEventPublisher.publish(orderErrorEvent);
				break;
			default :
				log.warn("Unsupported OrderEventType: {}", eventType);
		}
	}

}
