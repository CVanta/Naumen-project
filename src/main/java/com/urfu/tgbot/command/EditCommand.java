package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Класс предоставляет обработку команды /edit
 */
@Component
public class EditCommand {

    private final StateService stateService;
    private final UserService userService;

    @Autowired
    public EditCommand(StateService stateService, UserService userService) {
        this.stateService = stateService;
        this.userService = userService;
    }

    /**
     * Генерирует сообщение подтверждения от бота для редактирования профиля.
     * @param chatID Идентификатор чата пользователя.
     * @return Сообщение подтверждения от бота для редактирования профиля.
     */
    public String getBotText(long chatID) {
        String userString = userService.getUserByChatID(chatID).getFormattedString();
        return """
                В данный момент ваш профиль выглядит так:
                """ + userString + """
                 
                 Вы уверены что хотите изменить его? 
                (введите "Да" или "Нет")""";
    }

    /**
     * Обрабатывает ввод подтверждения пользователя для редактирования профиля.
     * @param input Ввод пользователя.
     * @param chatID Идентификатор чата пользователя.
     * @return Ответ бота на основе ввода пользователя.
     */
    public String handleConfirmInput(String input, long chatID) {
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkYesNo(input);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
        if (input.equals("Да")) {
            stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_NAME);
            return "Введите новое ФИО";
        }
        stateService.updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
        return "Бот ожидает следующей команды";
    }
}