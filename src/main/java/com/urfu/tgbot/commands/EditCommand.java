package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditCommand {

    private StateService stateService;
    private UserService userService;


    @Autowired
    public EditCommand(StateService stateService, UserService userService) {
        this.stateService = stateService;
        this.userService = userService;

    }

    public void updateState(long chatID) {
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
    }

    public String getBotText(long chatID) {
        String userString = userService.getUserByChatID(chatID).toString();
        return """
                В данный момент ваш профиль выглядит так:
                """ + userString + """
                 
                 Вы уверены что хотите изменить его? 
                (введите "Да" или "Нет")""";
    }

    public String handleConfirmInput(String input, long chatID) {
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkYesNo(input);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
        if (input.equals("Да")){
            stateService.updateState(chatID, States.WAITING_FOR_INPUT_NAME);
            return "Введите новое имя:";
        }
        stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
        return "Бот ожидает следующей команды";
    }

}

