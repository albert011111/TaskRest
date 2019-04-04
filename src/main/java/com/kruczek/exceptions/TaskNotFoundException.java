package com.kruczek.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task with given id is not found - id:[" + id + "]");
    }
}
