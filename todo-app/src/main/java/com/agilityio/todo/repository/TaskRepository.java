package com.agilityio.todo.repository;

import com.agilityio.todo.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Project: toto-app
 */
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findTasksByUserId(long userId);
    void deleteTasksByTaskId(long taskId);
    void deleteTasksByUserId(long userId);
}
