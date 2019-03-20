package com.kruczek.utils;

import com.kruczek.model.user.User;
import com.kruczek.services.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public final class Mapper {
    private Mapper() {
    }

    public static UserPrincipal userToUserPrincipal(User user, List<GrantedAuthority> authorities) {
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
