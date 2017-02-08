package com.agilityio.todo.web;

import com.agilityio.todo.security.jwt.TokenAuthenticationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Project: toto-app
 * Greeting resource
 */

@RestController
public class GreetingController {

    private TokenAuthenticationService tokenAuthenticationService;

    @RequestMapping("/greeting")
    public String greeting(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        tokenAuthenticationService = new TokenAuthenticationService();
        String userName = tokenAuthenticationService.getUsernameFromToken(token);
        return "Hi! " + userName;
    }
}
