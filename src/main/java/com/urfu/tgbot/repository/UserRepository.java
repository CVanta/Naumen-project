package com.urfu.tgbot.repository;

import com.urfu.tgbot.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий пользователей
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Возвращает объект 'User' с именем пользователя по умолчанию.
     *
     * @param username имя пользователя.
     * @return Объект 'User' с именем пользователя по умолчанию.
     */
    User findByUsername(String username);
}
