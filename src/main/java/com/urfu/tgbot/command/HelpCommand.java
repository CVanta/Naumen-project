package com.urfu.tgbot.command;

import org.springframework.stereotype.Component;

/**
 * Этот класс реализует команду /help для отображения списка доступных команд в боте Telegram.
 */
@Component
public class HelpCommand{

    /**
     * Показывает список доступных команд.
     * @return Список доступных команд.
     */
    public String getBotCommand(){
        return """
                Список доступных команд:<
                /start - старт бота
                /add - добавление поездки
                /edit - изменение профиля
                /help - помощь
                /list - вывод всех поездок
                /view - просмотр своих поездок
                /show - список пассажиров в поездке
                /profile - список поездок, на которые вы записаны
                """;
    }
}
