package com.kruczek.utils;

import com.kruczek.model.user.User;
import com.kruczek.services.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

public final class Mapper {
    private Mapper() {
    }

    public static UserPrincipal userToUserPrincipal(final User user, final List<GrantedAuthority> authorities) {
        Objects.requireNonNull(user, "user can't be null");
        Objects.requireNonNull(authorities, "authorities can't be null");

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
