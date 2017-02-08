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

/**
 * Project Name: todo-app
 * Created on 2/8/17.
 */

@RestController
public class TaskResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/todo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> createTodo(@Valid @RequestBody TaskDTO taskDTO, HttpServletRequest request) {
        // Get username from token
        String token = request.getHeader("Authorization");
        tokenAuthenticationService = new TokenAuthenticationService();
        String username = tokenAuthenticationService.getUsernameFromToken(token);

        // Get userId from username
        User user = userService.findUsersByUserName(username);

        if(user != null) {
            // Create a new task
            Task task = taskService.createTask(user.getUserId(), taskDTO.getTask(), taskDTO.getDescription());

            return new ResponseEntity(task, HttpStatus.CREATED);
        }

        return new ResponseEntity("Has error when create a new task.", HttpStatus.BAD_REQUEST);
    }
}
