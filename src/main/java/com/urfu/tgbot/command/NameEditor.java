package com.urfu.tgbot.command;

import com.urfu.tgbot.enums.StateEnum;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Этот класс реализует изменение профиля пользователя
 */
@Component
public class NameEditor {
    private final StateService stateService;
    private final UserService userService;

    @Autowired
    public NameEditor(StateService stateService, UserService userService) {
        this.stateService = stateService;
        this.userService = userService;
    }

    /**
     * Изменяет имя пользователя.
     *
     * @param chatID Идентификатор чата.
     * @param name   Имя пользователя.
     * @return Текст бота.
     * @throws Exception Если возникла ошибка при изменении имени пользователя.
     */
    public String editName(long chatID, String name) {
        User user = new User(name, chatID);
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkFullName(name);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
        User oldUser = userService.getUserByChatID(chatID);
        if (oldUser != null) {
            try {
                userService.changeUser(user);
            } catch (Exception e) {
                return "Не удалось установить имя";
            }
        }
        else {
            try {
                userService.addUser(user);
            } catch (Exception e) {
                return "Не удалось установить имя";
            }
        }
        stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_PHONE_NUMBER);
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
        User newUser = new User(user.getUsername(), chatID, user.getInstitute(), user.getPhoneNumber());
        userService.changeUser(user);
        stateService.updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
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
        User newUser = new User(user.getUsername(), chatID, user.getInstitute(), phoneNumber);
        userService.changeUser(newUser);
        stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_INSTITUTE);
        return "Введите ваш институт";
    }
}
