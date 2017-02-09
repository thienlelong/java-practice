package com.agilityio.todo.web;

import com.agilityio.todo.domain.User;
import com.agilityio.todo.security.jwt.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Project: toto-app
 * Greeting resource
 */

@RestController
public class GreetingController {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @RequestMapping("/greeting")
    public String greeting(HttpServletRequest request) {

        User user = tokenAuthenticationService.getUserFromToken(request);
        return "Hi! " + user.toString();
    }
}
