package com.example.TG_BOT.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Component
@Service
public class CommandManager{

    private final StartCommand startCommand;
    private final HelpCommand helpCommand;

    @Autowired
    public CommandManager(StartCommand startCommand, HelpCommand helpCommand) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
    }

    public String readCommand(String messageText) {
        switch (messageText) {
            case "/start":
                return startCommand.getBotText();
            case "/help":
                return helpCommand.getBotCommand();

            default: if(String.valueOf(messageText.charAt(0)).equals("/")) return "Не удалось разпознать команду";
            else {
                return "Ваше сообщение: " + messageText;
            }
        }
    }
}
