package com.agilityio.todo.repository;

import com.agilityio.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project: toto-app
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findUsersByUserId(long userId);

    User findUsersByUserName(String userName);

    User findUserByEmail(String email);
}
