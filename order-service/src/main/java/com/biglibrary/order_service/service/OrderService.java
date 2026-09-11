package com.biglibrary.order_service.service;

import com.biglibrary.order_service.dto.OrdersDTO;
import com.biglibrary.order_service.enums.OrderStatus;
import com.biglibrary.order_service.mapper.OrderEventMapper;
import com.biglibrary.order_service.mapper.OrderMapper;
import com.biglibrary.order_service.models.Orders;
import com.biglibrary.order_service.models.embedded_models.OrderCreatedEvent;
import com.biglibrary.order_service.models.embedded_models.OrderSummary;
import com.biglibrary.order_service.repository.OrderRepository;
import com.biglibrary.order_service.request.CreateOrderRequest;
import com.biglibrary.order_service.request.CreateOrderResponse;
import com.biglibrary.order_service.validation.OrderValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderValidation orderValidation;
	private final OrderEventService orderEventService;

	private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("INDIA", "USA", "GERMANY", "UK", "AUSTRALIA",
			"FRANCE", "ITALY", "CANADA", "JAPAN", "CHINA", "SOUTH KOREA", "CZECH REPUBLIC", "SLOVAKIA", "SLOVENIA",
			"POLAND", "DENMARK", "SWEDEN", "NORWAY", "FINLAND", "ICELAND", "SWITZERLAND", "AUSTRIA", "SPAIN",
			"PORTUGAL", "ITALY", "CROATIA", "SERBIA", "HUNGARY");

	public CreateOrderResponse createOrder(String userName, CreateOrderRequest createOrderRequest) {
		orderValidation.validateOrder(createOrderRequest);
		Orders newOrder = OrderMapper.mapToOrders(createOrderRequest);
		newOrder.setUserName(createOrderRequest.getUserName());
		Orders savedOrder = orderRepository.saveAndFlush(newOrder);
		log.info("Order created successfully with order number: {}", savedOrder.getOrderNumber());
		OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(savedOrder);
		orderEventService.save(orderCreatedEvent);
		return new CreateOrderResponse(savedOrder.getOrderNumber());
	}

	public void processNewOrders() {
		List<Orders> newOrders = orderRepository.findByStatus(OrderStatus.NEW);
		for (Orders order : newOrders) {
			this.process(order);
		}
	}

	private void process(Orders order) {
		try {
			log.info("Order can be delivered : {}", canBeDelivered(order));
			if (canBeDelivered(order)) {
				log.info("OrderNumber: {} can be delivered", order.getOrderNumber());
				orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.DELIVERED);
				orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(order));

			} else {
				log.info("OrderNumber: {} can not be delivered", order.getOrderNumber());
				orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
				orderEventService
						.save(OrderEventMapper.buildOrderCancelledEvent(order, "Can't deliver to the location"));
			}
		} catch (RuntimeException e) {
			log.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
			orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.ERROR);
			orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order, e.getMessage()));
		}
	}

	private boolean canBeDelivered(Orders order) {
		return DELIVERY_ALLOWED_COUNTRIES.contains(order.getDeliveryAddress().country().toUpperCase());
	}

	public List<OrderSummary> findOrders(String userName) {
		if (Objects.equals(userName, "") || userName == null) {
			return orderRepository.findAll().stream()
					.map(order -> new OrderSummary(order.getOrderNumber(), order.getStatus())).toList();
		}
		return orderRepository.findAllByUserName(userName).stream()
				.map(order -> new OrderSummary(order.getOrderNumber(), order.getStatus())).toList();
	}

	public OrdersDTO getOrderByOrderNumber(String orderNumber, String userName) {
		if (Objects.equals(userName, "") || userName == null) {
			return orderRepository.findByOrderNumber(orderNumber).map(OrderMapper::mapToOrdersDTO)
					.orElseThrow(() -> new RuntimeException("Order not found"));
		}
		return orderRepository.findByUserNameAndOrderNumber(userName, orderNumber).map(OrderMapper::mapToOrdersDTO)
				.orElseThrow(() -> new RuntimeException("Order not found"));
	}

}
