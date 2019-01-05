package com.kruczek.services;

import com.kruczek.model.role.Role;
import com.kruczek.model.role.RoleName;
import com.kruczek.model.user.User;
import com.kruczek.model.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!" + username));

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleName(RoleName.ROLE_USER);
        role.setId(1L);
        roles.add(role);
        user.setRoles(roles);
        LOGGER.info("User loaded from userRepository succesfully!!!");
        return UserPrincipal.createUserPrincipal(user);
    }
}
