package com.kruczek.auth.model.user;

import java.util.ArrayList;
import java.util.List;

import com.kruczek.task.dto.TaskDTO;

public class UserDTO {
	private Long id;
	private String name;
	private String username;
	private String email;
	private List<TaskDTO> tasks = new ArrayList<>();

	public UserDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}
}
