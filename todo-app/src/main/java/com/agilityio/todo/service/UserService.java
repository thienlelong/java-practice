package com.agilityio.todo.service;

import com.agilityio.todo.domain.User;

/**
 * Project: toto-app
 */

public interface UserService {
    User createUser(String userName, String fullName, String email, String password);

    User findUser(long userId);

    User findUsersByUserName(String userName);

    User findUserByEmail(String email);
}
