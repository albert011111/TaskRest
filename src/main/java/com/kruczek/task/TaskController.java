package com.kruczek.task;


import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:4201", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TaskController {

    private static final String TASKS = "/tasks";
    private static final String TASKS_TASK_ID = TASKS + "/{taskId}";

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @GetMapping(TASKS)
    public List<Task> getTasks() {
        return this.taskRepository.findAll();
    }

    @GetMapping(TASKS_TASK_ID)
    public Optional<Task> getTaskById(@PathVariable Long taskId) {
        return this.taskRepository.findById(taskId);
    }

    @PutMapping(TASKS)
    public Task addTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @DeleteMapping(TASKS_TASK_ID)
    public ResponseEntity<?> deleteTaskById(@PathVariable Long taskId) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElseThrow(() -> new ResourceNotFoundException("Task with id: " + taskId + " not found!"));
    }
}
