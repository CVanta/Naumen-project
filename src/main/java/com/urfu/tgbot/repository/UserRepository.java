package com.urfu.tgbot.repository;

import com.urfu.tgbot.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий пользователей
 */
public interface UserRepository extends CrudRepository<User, Long> {
}