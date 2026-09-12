package com.biglibrary.auth_service.controller;

import com.biglibrary.auth_service.dto.UserDTO;
import com.biglibrary.auth_service.response.ApiResponse;
import com.biglibrary.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<ApiResponse> getUserByTokenHandler(@RequestHeader("Authorization") String jwt) {
		UserDTO userDTO = userService.getUserByToken(jwt);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse("User Received Successfully", userDTO, HttpStatus.OK.value(), HttpStatus.OK));
	}

}
