package com.kruczek.task;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kruczek.auth.model.user.User;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

@Service
public class TaskServiceImpl {
	private final TaskRepository taskRepository;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = Objects.requireNonNull(taskRepository, getNpeDescription("taskRepository"));
	}

	Optional<Task> save(final Task task) {
		Objects.requireNonNull(task, getNpeDescription("task"));
		taskRepository.save(task);
		return Optional.of(task);
	}

	List<Task> getAll() {
		return taskRepository.findAll();
	}

	List<Task> getAllByUser(User user) {
		Objects.requireNonNull(user, getNpeDescription("username"));
		return taskRepository.findAllByUser(user);
	}

	Optional<Task> getById(Long id) {
		Objects.requireNonNull(id, getNpeDescription("id"));
		return taskRepository.findById(id);
	}

	public void delete(Task task) {
		Objects.requireNonNull(task, getNpeDescription("task"));
		taskRepository.delete(task);
	}


}

