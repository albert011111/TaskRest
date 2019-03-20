package com.kruczek.utils;

import com.kruczek.model.role.Role;
import com.kruczek.model.role.RoleName;
import com.kruczek.model.user.User;
import com.kruczek.services.UserPrincipal;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MapperTest {

    private User user;
    private UserPrincipal principal;

    @Before
    public void prepareData() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName(RoleName.ROLE_USER);

        HashSet<Role> roles = new HashSet<>();
        roles.add(role);

        user = new User("Pap", "pap", "pap@op.pl", "papPass");
        user.setId(100L);
        user.setRoles(roles);

        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority(RoleName.ROLE_USER.name());
        authorities.add(userAuthority);

        principal = Mapper.userToUserPrincipal(user, authorities);
    }

    @Test
    public void userToUserPrincipalTest() {
        assertThat(principal, CoreMatchers.instanceOf(UserPrincipal.class));
        assertThat("id should be equal", principal.getId(), CoreMatchers.is(user.getId()));
        assertEquals(principal.getAuthorities().size(), 1);
        assertEquals(principal.getEmail(), user.getEmail());
        assertEquals(principal.getPassword(), user.getPassword());
    }
}
