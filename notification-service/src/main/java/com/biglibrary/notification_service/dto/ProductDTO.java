package com.biglibrary.notification_service.dto;

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

	private String code;

	private String name;

	private String description;

	private String imageUrl;

	private BigDecimal price;
}
