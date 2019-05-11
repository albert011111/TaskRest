package com.kruczek.model.user;

import java.util.Objects;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

@Service
@Transactional
public class UserServiceImpl {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserByUsername(String username) {
		Objects.requireNonNull(username, getNpeDescription("username"));
		return userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!" + username));
	}
}
