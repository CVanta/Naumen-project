package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NameEditor {
    private final StateService stateService;
    private final UserService userService;

    @Autowired
    public NameEditor(StateService stateService, UserService userService) {
        this.stateService = stateService;
        this.userService = userService;
    }

    public String editName(long chatID, String name) throws Exception {
        User user = new User(name, chatID);
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
                .phoneNumber(user.getPhoneNumber())
                .institute(institute)
                .build();
        userService.deleteUser(newUser);
        userService.addUser(newUser);
        stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
        return "Вы успешно зарегистрировались. Ваш профиль:" + newUser.toString();
    }

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
                .institute(user.getInstitute())
                .phoneNumber(Long.parseLong(phoneNumber))
                .build();
        userService.deleteUser(newUser);
        userService.addUser(newUser);
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_INSTITUTE);
        return "Введите ваш институт";
    }


}
