package com.agilityio.todo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Project: toto-app
 */

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private long taskId;

    @NotNull
    private long userId;

    private String task;

    private String description;

    public Task(long userId, String task, String description) {
        this.userId = userId;
        this.task = task;
        this.description = description;
    }

    public Task() {

    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
