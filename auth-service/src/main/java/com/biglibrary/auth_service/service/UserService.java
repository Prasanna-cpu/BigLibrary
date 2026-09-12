package com.biglibrary.auth_service.service;

import com.biglibrary.auth_service.dto.UserDTO;
import com.biglibrary.auth_service.entity.User;
import com.biglibrary.auth_service.exception.UnauthorizedAccessException;
import com.biglibrary.auth_service.jwt.JWTProvider;
import com.biglibrary.auth_service.mapper.UserMapper;
import com.biglibrary.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, UnauthorizedAccessException.class})
public class UserService {

	private final UserRepository userRepository;
	private final JWTProvider jwtProvider;

	public UserDTO getUserByToken(String jwt) {
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UnauthorizedAccessException("User not found"));
		UserDTO userDTO = UserMapper.toDTO(user);
		return userDTO;
	}

}
