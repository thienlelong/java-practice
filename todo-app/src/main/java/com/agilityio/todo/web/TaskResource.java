package com.agilityio.todo.web;

import com.agilityio.todo.domain.Task;
import com.agilityio.todo.domain.User;
import com.agilityio.todo.security.jwt.TokenAuthenticationService;
import com.agilityio.todo.service.TaskService;
import com.agilityio.todo.service.UserService;
import com.agilityio.todo.web.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Project Name: todo-app
 * Created on 2/8/17.
 */

@RestController
public class TaskResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskResource.class);

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/todo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> createTodo(@Valid @RequestBody TaskDTO taskDTO, HttpServletRequest request) {
        // Get userId from token
        User user = tokenAuthenticationService.getUserFromToken(request);

        if (user != null) {
            // Create a new task
            Task task = taskService.createTask(user.getUserId(), taskDTO.getTask(), taskDTO.getDescription());

            return new ResponseEntity(task, HttpStatus.CREATED);
        }

        return new ResponseEntity("Has error when create a new task.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public ResponseEntity<?> getTodos(HttpServletRequest request) {
        User user = tokenAuthenticationService.getUserFromToken(request);

        if (user != null) {
            //Get list of tasks
            List<Task> tasks = taskService.findAllTasks(user.getUserId());

            return new ResponseEntity(tasks, HttpStatus.OK);
        }

        return new ResponseEntity("Has error when getting list of tasks.", HttpStatus.BAD_REQUEST);
    }
}
