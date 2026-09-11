package com.biglibrary.order_service.request;

import com.biglibrary.order_service.dto.AddressDTO;
import com.biglibrary.order_service.dto.CustomerDTO;
import com.biglibrary.order_service.dto.OrderItemsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
	@NotEmpty(message = "Order items are required")
	@Valid
	private Set<OrderItemsDTO> orderItems;
	@Valid
	private CustomerDTO customer;
	@Valid
	private AddressDTO address;
	private String userName;
}
