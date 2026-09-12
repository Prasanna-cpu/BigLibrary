package com.biglibrary.order_service.service;

import com.biglibrary.order_service.clients.UserServiceClient;
import com.biglibrary.order_service.dto.UserDTO;
import com.biglibrary.order_service.exceptions.ObjectNotFoundException;
import com.biglibrary.order_service.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

	private final UserServiceClient userServiceClient;

	private UserDTO getUserDTOFromResponse(String jwt) {
		ApiResponse apiResponse = userServiceClient.getUserByTokenHandler(jwt)
				.orElseThrow(() -> new ObjectNotFoundException("User not found"));
		UserDTO userDTO = userServiceClient.extractUserDTO(Optional.of(apiResponse))
				.orElseThrow(() -> new ObjectNotFoundException("User not found"));
		return userDTO;
	}

	public String getLoginUsername(String jwt) {
		UserDTO userDTO = getUserDTOFromResponse(jwt);
		return userDTO.getFullName().trim().toLowerCase();
	}

	public String getFullName(String jwt) {
		UserDTO userDTO = getUserDTOFromResponse(jwt);
		return userDTO.getFullName();
	}

	public String getEmail(String jwt) {
		UserDTO userDTO = getUserDTOFromResponse(jwt);
		return userDTO.getEmail();
	}

}
