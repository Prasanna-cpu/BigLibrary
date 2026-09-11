package com.biglibrary.notification_service.entity.embedded_models;



import com.biglibrary.notification_service.dto.AddressDTO;
import com.biglibrary.notification_service.dto.CustomerDTO;
import com.biglibrary.notification_service.dto.OrderItemsDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDeliveredEvent(String eventId, String orderNumber, CustomerDTO customerDTO,
								  Set<OrderItemsDTO> orderItems, AddressDTO deliveryAddress, LocalDateTime deliveredAt) {
}
