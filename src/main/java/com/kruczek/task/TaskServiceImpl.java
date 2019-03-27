package com.kruczek.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

@Service
public class TaskServiceImpl {

    @Autowired
    private TaskRepository taskRepository;

    public Optional<Task> save(final Task task) {
        Objects.requireNonNull(task, getNpeDescription("task"));
        taskRepository.save(task);
        return Optional.of(task);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> getById(Long id) {
        Objects.requireNonNull(id, getNpeDescription("id"));
        return taskRepository.findById(id);
    }

    public void delete(Task task) {
        Objects.requireNonNull(task, getNpeDescription("task"));
        taskRepository.delete(task);
    }

}

