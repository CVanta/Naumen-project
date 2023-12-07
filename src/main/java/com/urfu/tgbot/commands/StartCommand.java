package com.urfu.tgbot.commands;


import com.urfu.tgbot.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.urfu.tgbot.enums.States.WAITING_FOR_INPUT_NAME;
/**
 * Класс, представляющий обработку стартовой команды бота.
 */
@Controller
public class StartCommand {

    private final StateService stateService;

    @Autowired
    public StartCommand(StateService stateService) {
        this.stateService = stateService;
    }

    /**
     * Изменяет состояние чата на ожидание ввода имени пользователя.
     *
     * @param chatID Идентификатор чата пользователя.
     */
    public void changeState(long chatID) {
        stateService.saveState(chatID, WAITING_FOR_INPUT_NAME);
    }

    /**
     * Получает текст для бота с приветствием и приглашением зарегистрироваться.
     *
     * @return Текст для бота с приветствием и приглашением ввести ФИО.
     */
    public String getBotText() {
        return """
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """;
    }
}
