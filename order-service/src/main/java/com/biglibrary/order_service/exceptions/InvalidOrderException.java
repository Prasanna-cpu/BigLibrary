package com.biglibrary.order_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class InvalidOrderException extends RuntimeException {
	public InvalidOrderException(String message) {
		super(message);
	}
}
