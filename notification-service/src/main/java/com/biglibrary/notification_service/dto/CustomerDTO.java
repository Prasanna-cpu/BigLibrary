package com.biglibrary.notification_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	@NotBlank(message = "Customer name is required")
	private String name;
	@NotBlank(message = "Customer email is required")
	@Email(message = "Invalid email format")
	private String email;
	@NotBlank(message = "Customer phone is required")
	private String phone;

}
