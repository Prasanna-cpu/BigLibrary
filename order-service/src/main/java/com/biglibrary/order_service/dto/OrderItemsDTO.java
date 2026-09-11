package com.biglibrary.order_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {

	private UUID id;
	@NotBlank(message = "Item code is required")
	private String code;
	@NotBlank(message = "Item name is required")
	private String name;
	@NotNull(message = "Item price is required")
	@DecimalMin(value = "0.01", message = "Price must be greater than 0")
	private BigDecimal price;
	@NotNull(message = "Item quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;
	private UUID orderId;

}
