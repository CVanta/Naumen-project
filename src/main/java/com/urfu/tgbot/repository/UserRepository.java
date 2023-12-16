package com.urfu.tgbot.repository;

import com.urfu.tgbot.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий позтователей
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
