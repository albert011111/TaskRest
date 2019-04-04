package com.kruczek.task;


import com.kruczek.exceptions.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TaskController.API)
@PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
//@CrossOrigin(origins = "http://localhost:4201", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TaskController {
    static final String API = "/api";
    private static final String TASKS = "/tasks";
    private static final String TASKS_TASK_ID = TASKS + "/{taskId}";
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskServiceImpl taskService;

    @GetMapping(TASKS)
    public List<Task> getTasks() {
        LOGGER.warn("getTasks() invoked...");
        return taskService.getAll();
    }

    //TODO pozbyc sie TaskRepository!!! --> dodac warstwe serwisu dla pozostalych metod

    @Nullable
    @GetMapping(TASKS_TASK_ID)
    public Task getTaskById(@PathVariable Long taskId) {
        return taskService.getById(taskId).orElseGet(null);
    }

    @PutMapping(TASKS)
    public Task addTask(@RequestBody Task task) {
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
