package com.urfu.tgbot.service;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Класс обслуживания для управления пользователями
 */
@Service
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
     */
    public void addUser(User user){
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param user Пользователь для удаления.
     */
    public void deleteUser(User user) throws Exception {
        Optional<User> userOptional = userRepository.findById(user.getChatID());
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new Exception("User not exist");
        }
    }

    public void changeUser(User user) {
        try {
            deleteUser(user);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
        try {
            addUser(user);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Получает пользователя по идентификатору чата.
     *
     * @param chatID Идентификатор чата.
     * @return Пользователь с указанным идентификатором чата.
     */
    public User getUserByChatID(long chatID) {
        return userRepository.findById(chatID).orElse(null);
    }

    /**
     * Добавляет поездку к пользователю.
     *
     * @param trip Поездка для добавления.
     * @param user Пользователь, к которому добавить поездку.
     */
    public void addTripToUser(Trip trip, User user) throws Exception {
        Optional<User> currentUserOptional = userRepository.findById(user.getChatID());
        if (currentUserOptional.isPresent()) {
            trip.deletePassenger(currentUserOptional.get());
            trip.addPassenger(currentUserOptional.get());
            currentUserOptional.get().addTrip(trip);
            addUser(currentUserOptional.get());
        }
        else {
            throw new Exception("User not exist");
        }
    }
}