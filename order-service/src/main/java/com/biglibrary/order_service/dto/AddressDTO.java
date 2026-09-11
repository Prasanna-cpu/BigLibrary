package com.biglibrary.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

	@NotBlank(message = "AddressLine1 is required")
	private String addressLine1;
	private String addressLine2;
	@NotBlank(message = "City is required")
	private String city;
	@NotBlank(message = "State is required")
	private String state;
	@NotBlank(message = "ZipCode is required")
	private String zipCode;
	@NotBlank(message = "Country is required")
	private String country;

}
