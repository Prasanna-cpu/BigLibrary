package com.biglibrary.order_service.models.embedded_models;

import com.biglibrary.order_service.dto.AddressDTO;
import com.biglibrary.order_service.dto.CustomerDTO;
import com.biglibrary.order_service.dto.OrderItemsDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDeliveredEvent(String eventId, String orderNumber, CustomerDTO customerDTO,
		Set<OrderItemsDTO> orderItems, AddressDTO deliveryAddress, LocalDateTime deliveredAt) {
}
