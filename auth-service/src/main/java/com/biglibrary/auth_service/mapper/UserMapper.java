package com.biglibrary.auth_service.mapper;

import com.biglibrary.auth_service.dto.UserDTO;
import com.biglibrary.auth_service.entity.User;

public class UserMapper {

	public static UserDTO toDTO(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();

		userDTO.setId(user.getId());
		userDTO.setFullName(user.getFullName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setRole(user.getRole());
		return userDTO;
	}

	public static User toEntity(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		}

		User user = new User();

		if (userDTO.getId() != null) {
			user.setId(userDTO.getId());
		}

		user.setFullName(userDTO.getFullName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole());

		return user;

	}

}
