package com.biglibrary.notification_service.entity.embedded_models;



import com.biglibrary.notification_service.dto.AddressDTO;
import com.biglibrary.notification_service.dto.CustomerDTO;
import com.biglibrary.notification_service.dto.OrderItemsDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderCancelledEvent(String eventId, String orderNumber, Set<OrderItemsDTO> orderItems,
								  CustomerDTO customer, AddressDTO deliveryAddress, String reason, LocalDateTime createdAt) {
}
