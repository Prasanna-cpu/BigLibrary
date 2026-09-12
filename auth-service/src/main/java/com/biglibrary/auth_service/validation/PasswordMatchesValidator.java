package com.biglibrary.auth_service.validation;

import com.biglibrary.auth_service.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

	@Override
	public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext context) {
		if (registerRequest == null) {
			return true;
		}
		String password = registerRequest.getPassword();
		String confirmPassword = registerRequest.getConfirmPassword();

		if (password == null || confirmPassword == null) {
			return true;
		}

		return password.equals(confirmPassword);
	}
}
