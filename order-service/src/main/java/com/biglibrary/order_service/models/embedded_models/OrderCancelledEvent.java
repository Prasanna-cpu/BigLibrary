package com.biglibrary.order_service.models.embedded_models;

import com.biglibrary.order_service.dto.AddressDTO;
import com.biglibrary.order_service.dto.CustomerDTO;
import com.biglibrary.order_service.dto.OrderItemsDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderCancelledEvent(String eventId, String orderNumber, Set<OrderItemsDTO> orderItems,
		CustomerDTO customer, AddressDTO deliveryAddress, String reason, LocalDateTime createdAt) {
}
