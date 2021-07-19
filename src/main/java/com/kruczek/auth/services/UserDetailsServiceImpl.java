package com.kruczek.auth.services;

import java.util.Objects;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kruczek.auth.model.user.User;
import com.kruczek.auth.model.user.UserRepository;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = Objects.requireNonNull(userRepository, getNpeDescription("userRepository"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("loadUserByUsername started with username [{}]", username);

		Objects.requireNonNull(username, getNpeDescription("username"));
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!" + username));

		LOGGER.info("User {} loaded succesfully", user);
		return UserPrincipal.createUserPrincipal(user);
	}
}
