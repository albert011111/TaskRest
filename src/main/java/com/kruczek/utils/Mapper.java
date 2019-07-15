package com.kruczek.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

import com.kruczek.auth.model.user.User;
import com.kruczek.auth.services.UserPrincipal;
import com.kruczek.calendar.day.Day;
import com.kruczek.task.Task;
import com.kruczek.task.dto.TaskDTO;

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

	@Nullable
	public static Function<TaskDTO, Task> convertToTask(@Nullable User user, @Nullable Day day) {
		return taskDTO -> {
			if (taskDTO == null) {
				return null;
			}

			Task entity = new Task();
			entity.setId(taskDTO.getId());
			entity.setDay(day);
			entity.setUser(user);
			entity.setUserName(taskDTO.getUserName());
			entity.setCreateDate(taskDTO.getCreateDate());
			entity.setExecuteDate(taskDTO.getExecuteDate());
			entity.setDescription(taskDTO.getDescription());
			entity.setName(taskDTO.getName());
			entity.setFinished(taskDTO.isFinished());

			return entity;
		};
	}
}
