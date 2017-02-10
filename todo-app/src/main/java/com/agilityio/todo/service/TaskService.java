package com.agilityio.todo.service;

import com.agilityio.todo.domain.Task;

import java.util.List;

/**
 * Project: toto-app
 * Service interface for managing Tasks.
 */
public interface TaskService {
    Task createTask(long userId, String task, String description);

    List<Task> findAllTasks(long userId);

    void deleteTask(long taskId);

    void deleteAllTasks(long userId);
}
