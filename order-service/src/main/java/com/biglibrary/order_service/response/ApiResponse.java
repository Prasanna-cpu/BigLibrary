package com.biglibrary.order_service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

	private Object data;
	private Integer statusCode;
	private HttpStatus status;
	private String message;

}
