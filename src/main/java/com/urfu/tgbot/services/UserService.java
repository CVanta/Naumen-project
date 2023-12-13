package com.urfu.tgbot.services;

import com.urfu.tgbot.models.User;
import com.urfu.tgbot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addUser(User user) throws Exception {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            throw new Exception("user exist");
        }
        userRepository.save(user);
    }

    public void deleteUser(User user) throws Exception {
        User userFromDb = userRepository.findById(user.getChatID()).get();
        if (userFromDb == null) {
            throw new Exception("user not exist");
        }
        userRepository.delete(user);
    }

    public User getUserByChatID(long chatID) {
        return userRepository.findById(chatID).get();
    }
}
