package com.kruczek.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> getById(Long id) {
        Objects.requireNonNull(id, "id can't be null");
        return taskRepository.findById(id);
    }

    public void delete(Task task) {
        Objects.requireNonNull(task, "task can't be null");
        taskRepository.delete(task);
    }

}

