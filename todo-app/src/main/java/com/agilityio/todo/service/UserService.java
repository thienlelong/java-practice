package com.agilityio.todo.service;

import com.agilityio.todo.domain.User;

/**
 * Created by TriHo on 2/6/17.
 */
public interface UserService {
    User createUser(String userName, String fullName, String email, String password);
    User findUser(long userId);
}
