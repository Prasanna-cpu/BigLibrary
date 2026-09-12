package com.biglibrary.auth_service.service;

import com.biglibrary.auth_service.dto.UserDTO;
import com.biglibrary.auth_service.entity.User;
import com.biglibrary.auth_service.enums.UserRoles;
import com.biglibrary.auth_service.exception.BadRequestException;
import com.biglibrary.auth_service.exception.ConflictingResourcesException;
import com.biglibrary.auth_service.exception.UnauthorizedAccessException;
import com.biglibrary.auth_service.jwt.JWTProvider;
import com.biglibrary.auth_service.mapper.UserMapper;
import com.biglibrary.auth_service.repository.UserRepository;
import com.biglibrary.auth_service.request.LoginRequest;
import com.biglibrary.auth_service.request.RegisterRequest;
import com.biglibrary.auth_service.response.LoginResponse;
import com.biglibrary.auth_service.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailsServiceImplementation customUserDetailsService;
	private final JWTProvider jwtProvider;

	private Authentication authenticateUser(String email, String password) {
		UserDetails details = customUserDetailsService.loadUserByUsername(email);

		// if(details == null){
		// throw new BadCredentialsException("Invalid credentials");
		// }

		if (!passwordEncoder.matches(password, details.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
	}

	public RegisterResponse register(RegisterRequest request) {
		boolean userExists = userRepository.existsByEmail(request.getEmail());
		if (userExists) {
			throw new ConflictingResourcesException("User already exists");
		}
		String requestedRole = request.getRole() == null ? null : request.getRole().toString();

		if (requestedRole == null || requestedRole.isEmpty()) {
			requestedRole = "ROLE_USER";
		} else if (!requestedRole.equals("ROLE_USER") && !requestedRole.equals("ROLE_ADMIN")) {
			throw new BadRequestException("Invalid role");
		}

		User user = new User();

		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(UserRoles.valueOf(requestedRole));

		User savedUser = userRepository.save(user);
		UserDTO savedUserDTO = UserMapper.toDTO(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUserDTO.getEmail(),
				savedUserDTO.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accessToken = jwtProvider.generateAccessToken(authentication);
		String refreshToken = jwtProvider.generateRefreshToken(authentication);

		return new RegisterResponse(savedUserDTO, accessToken, refreshToken);
	}

	public LoginResponse login(LoginRequest request) {
		boolean isUserExists = userRepository.existsByEmail(request.getEmail());
		if (!isUserExists) {
			throw new UnauthorizedAccessException("User with email " + request.getEmail() + " does not exist");
		}

		String email = request.getEmail();
		String password = request.getPassword();

		Authentication authentication = authenticateUser(email, password);

		String accessToken = jwtProvider.generateAccessToken(authentication);
		String refreshToken = jwtProvider.generateRefreshToken(authentication);

		LoginResponse response = new LoginResponse(accessToken, refreshToken);

		return response;
	}

}
