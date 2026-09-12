package com.biglibrary.auth_service.controller;

import com.biglibrary.auth_service.request.LoginRequest;
import com.biglibrary.auth_service.request.RegisterRequest;
import com.biglibrary.auth_service.response.ApiResponse;
import com.biglibrary.auth_service.response.LoginResponse;
import com.biglibrary.auth_service.response.RegisterResponse;
import com.biglibrary.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> registerHandler(@Valid @RequestBody RegisterRequest request) {
		RegisterResponse response = authService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User Registered Successfully", response,
				HttpStatus.CREATED.value(), HttpStatus.CREATED));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> loginHandler(@Valid @RequestBody LoginRequest request) {
		LoginResponse response = authService.login(request);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse("User Logged In Successfully", response, HttpStatus.OK.value(), HttpStatus.OK));
	}

}
