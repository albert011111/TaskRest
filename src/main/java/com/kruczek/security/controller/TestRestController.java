package com.kruczek.security.controller;

import com.kruczek.utils.Commons;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Commons.API)
public class TestRestController {

    // adnotacja decyduje czy metoda moze zostac wywolana -- przed jej uruchomieniem
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @GetMapping(Commons.TEST_ALL)
    public String checkUserAndAdminAccess() {
        return "Content for USER and ADMIN";
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(Commons.TEST_ADMIN)
    public String checkAdminAccess() {
        return "Content for ADMIN";
    }

    @PreAuthorize(value = "hasRole('USER')")
    @GetMapping(Commons.TEST_USER)
    public String checkUserAccess() {
        return "Content for USER";
    }


}
