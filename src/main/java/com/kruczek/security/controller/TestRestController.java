package com.kruczek.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kruczek.utils.Commons.API;
import static com.kruczek.utils.Commons.TEST_ADMIN;
import static com.kruczek.utils.Commons.TEST_ALL;
import static com.kruczek.utils.Commons.TEST_USER;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(API)
public class TestRestController {

	@PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
	@GetMapping(TEST_ALL)
	public String checkUserAndAdminAccess() {
		return "Content for USER and ADMIN";
	}

	@PreAuthorize(value = "hasRole('ADMIN')")
	@GetMapping(TEST_ADMIN)
	public String checkAdminAccess() {
		return "Content for ADMIN";
	}

	@PreAuthorize(value = "hasRole('USER')")
	@GetMapping(TEST_USER)
	public String checkUserAccess() {
		return "Content for USER";
	}
}
