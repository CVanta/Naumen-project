package com.urfu.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class HelpCommand {

    public String getBotCommand() {
        return """
                Список доступных команд:<
                /start - старт бота
                /edit - изменение профиля
                /help - список доступных команд""";
    }

}
