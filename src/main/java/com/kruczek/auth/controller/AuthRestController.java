package com.kruczek.auth.controller;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruczek.auth.message.request.LoginForm;
import com.kruczek.auth.message.request.RegisterForm;
import com.kruczek.auth.message.response.JwtResponse;
import com.kruczek.auth.message.response.ResponseMessage;
import com.kruczek.auth.model.role.Role;
import com.kruczek.auth.model.role.RoleName;
import com.kruczek.auth.model.role.RoleRepository;
import com.kruczek.auth.model.user.User;
import com.kruczek.auth.model.user.UserRepository;
import com.kruczek.security.jwt.JwtProvider;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class AuthRestController {

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;

	public AuthRestController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.roleRepository = roleRepository;
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginForm loginRequest) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtProvider.createJwtToken(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwtToken, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm registerRequest) {
		Objects.requireNonNull(registerRequest, "registerRequest can't be null");

		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Error -> This username already exists in DB"),
					HttpStatus.NOT_ACCEPTABLE);
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Error -> This email already exists in DB"),
					HttpStatus.NOT_ACCEPTABLE);
		}

		User newUser = new User(
				registerRequest.getName(),
				registerRequest.getUsername(),
				registerRequest.getEmail(),
				encoder.encode(registerRequest.getPassword())
		);
		Set<String> registerRoles = registerRequest.getRole();
		Set<Role> userRoles = new HashSet<>();


		registerRoles.forEach(role -> {
			if (role.equalsIgnoreCase("USER")) {
				Role userRole = roleRepository
						.findByRoleName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail -> ROLE_USER not found in DB."));
				userRoles.add(userRole);
			}
			if (role.equalsIgnoreCase("ADMIN")) {
				Role userRole = roleRepository
						.findByRoleName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail -> ROLE_ADMIN not found in DB."));
				userRoles.add(userRole);
			}
		});

		newUser.setRoles(userRoles);
		userRepository.save(newUser);

		return new ResponseEntity<>(new ResponseMessage("Success! New user has been created!"), HttpStatus.OK);
	}


}
