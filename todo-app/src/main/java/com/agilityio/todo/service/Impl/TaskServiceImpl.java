package com.agilityio.todo.service.Impl;

import com.agilityio.todo.domain.Task;
import com.agilityio.todo.repository.TaskRepository;
import com.agilityio.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by TriHo on 2/6/17.
 */
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task createTask(long userId, String task, String description) {
        Task taskObj = new Task(userId, task, description);
        return taskRepository.save(taskObj);
    }

    @Override
    public List<Task> findAllTasks(long userId) {
        return taskRepository.findTasksByUserId(userId);
    }

    @Override
    public void deleteTask(long taskId) {
        taskRepository.deleteTasksByTaskId(taskId);
    }

    @Override
    public void deleteAllTasks(long userId) {
        taskRepository.deleteTasksByUserId(userId);
    }
}
