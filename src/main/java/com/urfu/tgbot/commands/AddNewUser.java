package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AddNewUser {
    private final StateService stateService;
    private final UserService userService;

    @Autowired
    public AddNewUser(StateService stateService, UserService userService) {
        this.stateService = stateService;
        this.userService = userService;
    }

    /**
     * Изменяет имя пользователя.
     *
     * @param chatID Идентификатор чата.
     * @param name Имя пользователя.
     * @param username Ник пользователя.
     * @return Текст бота.
     * @throws Exception Если возникла ошибка при изменении имени или ника пользователя.
     */
    public String editName(long chatID, String name, String username) throws Exception {
        User user = new User(name, chatID, username);
        if(userService.isUserExists(chatID)) {
            User beforeEditUser = userService.getUserByChatID(chatID);
            if(beforeEditUser.getTripList() != null){
                user.editTripList(beforeEditUser);
            }
        }
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkFullName(name);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
        try {
            userService.addUser(user);
        } catch (Exception e) {
            userService.deleteUser(user);
            userService.addUser(user);
        }
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_PHONE_NUMBER);
        return "Введите номер телефона";
    }

    /**
     * Изменяет институт пользователя.
     *
     * @param chatID Идентификатор чата.
     * @param institute Институт пользователя.
     * @return Текст бота.
     * @throws Exception Если возникла ошибка при изменении института пользователя.
     */
    public String editInstitute(long chatID, String institute) throws Exception {
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkInstitute(institute);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
        User user = userService.getUserByChatID(chatID);
        User newUser = new User.Builder(chatID)
                .username(user.getUsername())
                .tgUsername(user.getTgUsername())
                .phoneNumber(user.getPhoneNumber())
                .institute(institute)
                .build();
        userService.deleteUser(newUser);
        userService.addUser(newUser);
        stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
        return "Вы успешно зарегистрировались. Ваш профиль:" + newUser.getFormattedString();
    }

    /**
     * Изменяет номер телефона пользователя.
     *
     * @param chatID Идентификатор чата.
     * @param phoneNumber Номер телефона пользователя.
     * @return Текст бота.
     * @throws Exception Если возникла ошибка при изменении номера телефона пользователя.
     */
    public String editPhoneNumber(long chatID, String phoneNumber) throws Exception {
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkPhoneNumber(phoneNumber);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
        User user = userService.getUserByChatID(chatID);
        User newUser = new User.Builder(chatID)
                .username(user.getUsername())
                .tgUsername(user.getTgUsername())
                .phoneNumber(Long.parseLong(phoneNumber))
                .build();
        userService.deleteUser(newUser);
        userService.addUser(newUser);
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_INSTITUTE);
        return "Введите ваш институт";
    }
}