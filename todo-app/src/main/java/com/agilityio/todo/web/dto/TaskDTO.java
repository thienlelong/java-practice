package com.agilityio.todo.web.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Project Name: todo-app
 */
public class TaskDTO {
    @NotEmpty
    private String task;

    @NotEmpty
    private String description;

    public TaskDTO(String task, String description) {
        this.task = task;
        this.description = description;
    }

    public TaskDTO(){

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
