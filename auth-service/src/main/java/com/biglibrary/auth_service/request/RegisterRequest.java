package com.biglibrary.auth_service.request;

import com.biglibrary.auth_service.enums.UserRoles;
import com.biglibrary.auth_service.validation.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@PasswordMatches
public class RegisterRequest {

	@NotBlank(message = "Full name is required")
	private String fullName;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is required")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "password is required")
	private String password;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "confirm password is required")
	private String confirmPassword;

	private UserRoles role;

}
