package com.biglibrary.catalog_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private String id;

	@NotEmpty(message = "Product code cannot be empty")
	private String code;

	@NotEmpty(message = "Product name cannot be empty")
	private String name;

	private String description;

	private String imageUrl;

	@NotNull(message = "Product price cannot be null")
	@DecimalMin(value = "0.0", message = "Product price cannot be negative")
	private BigDecimal price;
}
