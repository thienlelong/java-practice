package com.agilityio.todo.web;

import com.agilityio.todo.domain.User;
import com.agilityio.todo.service.UserService;
import com.agilityio.todo.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Project: toto-app
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);

        return Optional.ofNullable(userService.findUsersByUserName(userDTO.getUserName()))
                .map(user -> new ResponseEntity<>("User Name already in use", httpHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    if (userService.findUserByEmail(userDTO.getEmail()) != null) {
                        return new ResponseEntity<>("Email already in use", httpHeaders, HttpStatus.BAD_REQUEST);
                    }

                    User user = userService.createUser(userDTO.getUserName(), userDTO.getFullName(),
                            userDTO.getEmail(), userDTO.getPassword());
                    return new ResponseEntity(user, HttpStatus.CREATED);
                });
    }
}