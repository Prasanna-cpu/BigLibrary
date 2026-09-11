package com.biglibrary.order_service.mapper;

import com.biglibrary.order_service.dto.OrderItemsDTO;
import com.biglibrary.order_service.models.Orders;
import com.biglibrary.order_service.models.embedded_models.OrderCancelledEvent;
import com.biglibrary.order_service.models.embedded_models.OrderCreatedEvent;
import com.biglibrary.order_service.models.embedded_models.OrderDeliveredEvent;
import com.biglibrary.order_service.models.embedded_models.OrderErrorEvent;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderEventMapper {

	private static Set<OrderItemsDTO> getOrderItems(Orders order) {
		return order.getOrderItems().stream().map(OrderItemsMapper::mapToOrderItemsDTO).collect(Collectors.toSet());
	}

	public static OrderCreatedEvent buildOrderCreatedEvent(Orders orders) {
		return new OrderCreatedEvent(UUID.randomUUID().toString(), orders.getOrderNumber(), getOrderItems(orders),
				CustomerMapper.mapToCustomerDTO(orders.getCustomer()),
				AddressMapper.mapToAddressDTO(orders.getDeliveryAddress()), LocalDateTime.now());
	}

	public static OrderDeliveredEvent buildOrderDeliveredEvent(Orders orders) {
		return new OrderDeliveredEvent(UUID.randomUUID().toString(), orders.getOrderNumber(),
				CustomerMapper.mapToCustomerDTO(orders.getCustomer()), getOrderItems(orders),
				AddressMapper.mapToAddressDTO(orders.getDeliveryAddress()), LocalDateTime.now());
	}

	public static OrderCancelledEvent buildOrderCancelledEvent(Orders orders, String reason) {
		return new OrderCancelledEvent(UUID.randomUUID().toString(), orders.getOrderNumber(), getOrderItems(orders),
				CustomerMapper.mapToCustomerDTO(orders.getCustomer()),
				AddressMapper.mapToAddressDTO(orders.getDeliveryAddress()), reason, LocalDateTime.now());
	}

	public static OrderErrorEvent buildOrderErrorEvent(Orders orders, String reason) {
		return new OrderErrorEvent(UUID.randomUUID().toString(), orders.getOrderNumber(), getOrderItems(orders),
				CustomerMapper.mapToCustomerDTO(orders.getCustomer()),
				AddressMapper.mapToAddressDTO(orders.getDeliveryAddress()), reason, LocalDateTime.now());
	}

}
