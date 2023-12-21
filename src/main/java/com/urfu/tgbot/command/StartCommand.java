package com.urfu.tgbot.command;


import org.springframework.stereotype.Component;

/**
 * Класс, представляющий обработку стартовой команды бота.
 */
@Component
public class StartCommand {

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
