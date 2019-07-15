package com.kruczek.wallet.controller;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruczek.auth.model.user.User;
import com.kruczek.auth.model.user.UserRepository;

import static com.kruczek.utils.Commons.API;
import static com.kruczek.utils.Commons.WALLET;

@RestController
@RequestMapping(API + WALLET)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class WalletController {
	private final UserRepository userRepository;

	public WalletController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/test")
	public void testWalletRequest(@RequestBody WalletWrapperDTO walletWrapperDTO) {
		final String username = walletWrapperDTO.getUsername();
		final Optional<User> userOpt = userRepository.findByUsername(username);
		final User user = userOpt.orElseThrow(EntityNotFoundException::new);

		System.out.println(user.getId());
	}

}
