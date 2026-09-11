package com.biglibrary.order_service.models.embedded_models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Customer(@NotBlank(message = "Customer name must not be blank") String name,
		@NotBlank(message = "Customer email must not be blank") @Email String email,
		@NotBlank(message = "Customer phone must not be blank") String phone) {
}
