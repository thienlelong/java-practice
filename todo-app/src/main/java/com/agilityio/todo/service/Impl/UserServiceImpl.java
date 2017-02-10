package com.agilityio.todo.service.Impl;

import com.agilityio.todo.domain.User;
import com.agilityio.todo.repository.UserRepository;
import com.agilityio.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Project: toto-app
 * Service class for managing Users.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(String userName, String fullName, String email, String password) {
        User user = new User(userName, fullName, email, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User findUser(long userId) {
        return userRepository.findUsersByUserId(userId);
    }

    @Override
    public User findUsersByUserName(String userName) {
        return userRepository.findUsersByUserName(userName);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
