package com.kruczek.auth.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kruczek.auth.model.user.User;
import com.kruczek.utils.Mapper;

public class UserPrincipal implements UserDetails {
    private long id;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;

    /*GrantedAuthority - klasa reprezentujaca, jakie użytkownik ma autoryzacje
    @see getAuthority()
    */
    private List<? extends GrantedAuthority> authorities;

    public UserPrincipal(long id,
                         String name,
                         String username,
                         String email,
                         String password,
                         List<? extends GrantedAuthority> authorities) {
        this.id = Objects.requireNonNull(id, "id can't be null");
        this.name = Objects.requireNonNull(name, "name can't be null");
        this.username = Objects.requireNonNull(username, "username can't be null");
        this.email = Objects.requireNonNull(email, "email can't be null");
        this.password = Objects.requireNonNull(password, "password can't be null");
        this.authorities = Objects.requireNonNull(authorities, "authorities can't be null");
    }

    public static UserPrincipal createUserPrincipal(final User user) {
        Objects.requireNonNull(user, "user can't be null");

        final List<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        return Mapper.userToUserPrincipal(user, grantedAuthorities);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal user = (UserPrincipal) o;

        return Objects.equals(user, o);
    }
}
