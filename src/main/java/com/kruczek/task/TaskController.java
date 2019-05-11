package com.kruczek.task;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruczek.exceptions.TaskNotFoundException;
import com.kruczek.model.user.UserServiceImpl;
import com.kruczek.services.UserDetailsServiceImpl;

import static com.kruczek.task.TaskController.API;

@RestController
@RequestMapping(API)
@PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
//@CrossOrigin(origins = "http://localhost:4201", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TaskController {
	static final String API = "/api";
	private static final String TASKS = "/tasks";
	private static final String TASKS_TASK_ID = TASKS + "/{taskId}";
	private static final String TASKS_USERNAME = TASKS + "/users/{userName}";
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	private final TaskServiceImpl taskService;
	private final UserServiceImpl userService;

	@Autowired
	public TaskController(TaskServiceImpl taskService, UserDetailsServiceImpl userService, UserServiceImpl userService1) {
		this.taskService = taskService;
		this.userService = userService1;
	}

	@GetMapping(TASKS)
	public List<Task> getTasks() {
		LOGGER.warn("getTasks() invoked...");
		return taskService.getAll();
	}

	@GetMapping(TASKS_USERNAME)
	public List<Task> getTasksByUser(@PathVariable String userName) {
		LOGGER.warn("getTasks() invoked...");

		return taskService.getAllByUser(userService.getUserByUsername(userName));
	}

	@Nullable
	@GetMapping(TASKS_TASK_ID)
	public Task getTaskById(@PathVariable Long taskId) {
		return taskService.getById(taskId).orElseGet(null);
	}

	@PutMapping(TASKS)
	public Task addTask(@RequestBody Task task) {
		task.setUser(userService.getUserByUsername(task.getUserName()));

		return taskService.save(task)
				.orElseThrow(() -> new ResourceNotFoundException("Save process failed!"));
	}

	@DeleteMapping(TASKS_TASK_ID)
	public ResponseEntity<?> deleteTaskById(@PathVariable Long taskId) {
		return taskService.getById(taskId)
				.map(task -> {
					taskService.delete(task);
					return ResponseEntity.ok(task);
				})
				.orElseThrow(() -> new TaskNotFoundException(taskId));
	}
}
