package com.kruczek.task;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import com.kruczek.calendar.day.DayService;
import com.kruczek.exceptions.TaskNotFoundException;
import com.kruczek.model.user.UserServiceImpl;
import com.kruczek.services.UserDetailsServiceImpl;
import com.kruczek.task.dto.TaskDTO;

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
	private final DayService dayService;

	@Autowired
	public TaskController(TaskServiceImpl taskService,
			UserDetailsServiceImpl userService,
			UserServiceImpl userService1,
			DayService dayService) {
		this.taskService = taskService;
		this.userService = userService1;
		this.dayService = dayService;
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
		LOGGER.info("Fetching task with id={} started", taskId);
		try {
			return taskService.getById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
		} catch (TaskNotFoundException ex) {
			LOGGER.warn("Fetching task with id={} failed", taskId);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Day with id:" + taskId + " not found", ex);
		}
	}

	@PutMapping(TASKS)
	public Task addTask(@RequestBody TaskDTO taskDTO) {
/*		taskDTO.setUser(userService.getUserByUsername(taskDTO.getUserName()));

		return taskService.save(taskDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Task save process failed!"));*/

		//TODO koniecznie wykonac konwerter a najlepiej dolaczyc mappera
		final Task taskEntity = new Task();

		taskEntity.setUser(userService.getUserByUsername(taskDTO.getUserName()));
		taskEntity.setCreateDate(taskDTO.getCreateDate());
		taskEntity.setExecuteDate(taskDTO.getExecuteDate());
		taskEntity.setName(taskDTO.getName());
		taskEntity.setDescription(taskDTO.getDescription());
		taskEntity.setDay(dayService.getDay(taskDTO.getDayId()).orElse(null));
		LOGGER.info("Request with taskDto: " + taskDTO);
		return null;
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
