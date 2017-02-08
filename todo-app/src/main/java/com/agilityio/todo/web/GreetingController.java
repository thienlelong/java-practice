package com.agilityio.todo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Project: toto-app
 * Greeting resource
 */

@RestController
public class GreetingController {

    private static final String template = "Hello, Todo!";

    @RequestMapping("/greeting")
    public String greeting() {
        return template;
    }
}
