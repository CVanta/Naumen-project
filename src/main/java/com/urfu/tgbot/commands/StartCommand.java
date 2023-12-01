package com.urfu.tgbot.commands;


import com.urfu.tgbot.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.urfu.tgbot.enums.States.WAITING_FOR_INPUT_NAME;

@Controller
public class StartCommand {

    private final StateService stateService;

    @Autowired
    public StartCommand(StateService stateService) {
        this.stateService = stateService;
    }

    /**
     * Обновляет состояние чата на `WAITING_FOR_INPUT_NAME`.
     *
     * @param chatID Идентификатор чата пользователя.
     */
    public void changeState(long chatID) {
        stateService.saveState(chatID, WAITING_FOR_INPUT_NAME);
    }


    /**
     * Генерирует приветственное сообщение бота.
     * @return Приветственное сообщение бота.
     */
    public String getBotText() {
        return """
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """;
    }
}
