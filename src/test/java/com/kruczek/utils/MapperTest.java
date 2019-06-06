package com.kruczek.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kruczek.auth.model.role.Role;
import com.kruczek.auth.model.role.RoleName;
import com.kruczek.auth.model.user.User;
import com.kruczek.auth.services.UserPrincipal;
import com.kruczek.calendar.day.Day;
import com.kruczek.task.Task;
import com.kruczek.task.dto.TaskDTO;

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
		HashSet<Role> roles = new HashSet<>(Collections.singletonList(role));

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

	@Test
	public void convertToTaskTest() {
		//given
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(100L);
		taskDTO.setName("Zadanie");
		taskDTO.setDescription("Opis");
		taskDTO.setFinished(false);
		taskDTO.setUserName("fakeUser");

		User user = new User();
		user.setId(1L);
		user.setUsername("papryk");

		Day day = null;

		//when
		Task entity = Mapper.convertToTask(user, day).apply(taskDTO);

		//then
		Assertions.assertThat(entity.getId()).isEqualTo(100L);
		Assertions.assertThat(entity.getUser()).isNotNull();
		Assertions.assertThat(entity.getUser().getUsername()).isEqualTo("papryk");
		Assertions.assertThat(entity.getUserName()).isEqualTo("fakeUser");
		Assertions.assertThat(entity.getDay()).isNull();
		Assertions.assertThat(entity.isFinished()).isFalse();
	}
}
