package com.urfu.tgbot.commands;

import org.springframework.stereotype.Component;

/**
 * Этот класс реализует команду /help для отображения списка доступных команд в боте Telegram.
 */
@Component
public class HelpCommand {

    /**
     * Показывает список доступных команд.
     * @return Список доступных команд.
     */
    public String getBotCommand() {
        return """
                Список доступных команд:<
                /start - старт бота
                /edit - изменение профиля
                /help - список доступных команд""";
    }
}
