package com.urfu.tgbot.service;

import com.urfu.tgbot.model.User;
import com.urfu.tgbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Класс обслуживания для управления пользователями
 */
@Component
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Добавляет нового пользователя в базу данных.
     *
     * @param user Пользователь для добавления.
     * @throws Exception Если пользователь уже существует.
     */
    public void addUser(User user) throws Exception {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            throw new Exception("user exist");
        }
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param user Пользователь для удаления.
     * @throws Exception Если пользователь не существует.
     */
    public void deleteUser(User user) throws Exception {
        User userFromDb = userRepository.findById(user.getChatID()).get();
        if (userFromDb == null) {
            throw new Exception("user not exist");
        }
        userRepository.delete(user);
    }

    /**
     * Получает пользователя по идентификатору чата.
     *
     * @param chatID Идентификатор чата.
     * @return Пользователь с указанным идентификатором чата.
     */
    public User getUserByChatID(long chatID) {
        return userRepository.findById(chatID).get();
    }
}
