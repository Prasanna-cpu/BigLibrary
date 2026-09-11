package com.biglibrary.notification_service.dto;

import com.biglibrary.notification_service.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {

	private UUID id;
	private String orderNumber;
	private String userName;
	private Set<OrderItemsDTO> orderItems;
	private CustomerDTO customer;
	private AddressDTO deliveryAddress;
	private OrderStatus status;
	private String comments;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public BigDecimal getTotalAmount() {
		return orderItems.stream()
				.map(orderItem -> orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
