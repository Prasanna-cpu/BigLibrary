package com.biglibrary.order_service.exceptions;

import com.biglibrary.order_service.response.ApiResponse;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> validationErrors = new HashMap<>();
		List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

		validationErrorList.forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String validationMessage = error.getDefaultMessage();
			validationErrors.put(fieldName, validationMessage);
		});
		return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ApiResponse> handleObjectNotFoundException(ObjectNotFoundException ex) {
		ApiResponse apiResponse = new ApiResponse(null, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
				ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidOrderException.class)
	public ResponseEntity<ApiResponse> handleInvalidOrderException(InvalidOrderException ex) {
		ApiResponse apiResponse = new ApiResponse(null, HttpStatus.UNPROCESSABLE_CONTENT.value(),
				HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.UNPROCESSABLE_CONTENT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ApiResponse apiResponse = new ApiResponse(null, HttpStatus.UNPROCESSABLE_CONTENT.value(),
				HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.UNPROCESSABLE_CONTENT);
	}

}
