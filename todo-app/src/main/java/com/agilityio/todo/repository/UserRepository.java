package com.agilityio.todo.repository;

import com.agilityio.todo.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by TriHo on 2/6/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findUsersByUserId(long userId);
}
