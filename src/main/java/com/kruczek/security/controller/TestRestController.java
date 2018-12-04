package com.kruczek.security.controller;

import com.kruczek.utils.PathConstans;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PathConstans.API)
public class TestRestController {

    // adnotacja decyduje czy metoda moze zostac wywolana -- przed jej uruchomieniem
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @GetMapping(PathConstans.API + PathConstans.TEST_ALL)
    public String checkUserAndAdminAccess() {
        return "Content for USER and ADMIN";
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @GetMapping(PathConstans.API + PathConstans.TEST_ADMIN)
    public String checkAdminAccess() {
        return "Content for ADMIN";
    }

    @PreAuthorize(value = "hasRole('USER')")
    @GetMapping(PathConstans.API + PathConstans.TEST_USER)
    public String checkUserAccess() {
        return "Content for USER";
    }


}
