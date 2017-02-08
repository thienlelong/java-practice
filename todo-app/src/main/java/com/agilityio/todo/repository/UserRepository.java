package com.agilityio.todo.repository;

import com.agilityio.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project: toto-app
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findUsersByUserId(long userId);

    User findUsersByUserName(String userName);

    User findUserByEmail(String email);
}
