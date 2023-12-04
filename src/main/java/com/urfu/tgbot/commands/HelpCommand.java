package com.urfu.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class HelpCommand{

    /**
     * Генерирует список доступных команд.
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
