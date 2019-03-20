package com.kruczek.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4201", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:4201", maxAge = 3600)
@RestController
@RequestMapping(TaskController.API)
public class TaskController {

    static final String API = "/api";
    private static final String TASKS = "/tasks";
    private static final String TASKS_TASK_ID = TASKS + "/{taskId}";

    @Autowired
    private TaskServiceImpl taskService;

    private final TaskRepository taskRepository;


    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @GetMapping(TASKS)
    public List<Task> getTasks() {
        return taskService.getAll();
    }

    //TODO pozbyc sie TaskRepository!!! --> dodac warstwe serwisu dla pozostalych metod

    @GetMapping(TASKS_TASK_ID)
    public Optional<Task> getTaskById(@PathVariable Long taskId) {
        return taskService.getById(taskId);
    }

    @PutMapping(TASKS)
    public Task addTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @DeleteMapping(TASKS_TASK_ID)
    public ResponseEntity<?> deleteTaskById(@PathVariable Long taskId) {
        return taskService.getById(taskId)
                .map(task -> {
                    taskRepository.delete(task);
//                    return ResponseEntity.status(HttpStatus.OK).build();
                    return ResponseEntity.ok(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Task with id: " + taskId + " not found!"));
    }
}
