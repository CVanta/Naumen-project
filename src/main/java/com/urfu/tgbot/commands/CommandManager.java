package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


@Component
@Controller
public class CommandManager {

    private final StartCommand startCommand;
    private final HelpCommand helpCommand;

    private final ListCommand listCommand;

    private final StateService stateService;

    private final NameEditor nameEditor;


    @Autowired
    public CommandManager(StartCommand startCommand, HelpCommand helpCommand, ListCommand listCommand, StateService stateService, NameEditor nameEditor) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
        this.listCommand = listCommand;
        this.stateService = stateService;
        this.nameEditor = nameEditor;
    }


    public String readInput(String messageText, long chatId) {
        States state = stateService.getState(chatId);
        String answer = messageText;
        switch (state) {
            case null -> {
                startCommand.changeState(chatId);
                answer = startCommand.getBotText();
            }
            case WAITING_FOR_INPUT_NAME -> {
                try {
                    answer = nameEditor.editName(chatId, messageText);
                } catch (Exception e) {
                    return "Вы не изменили имя";
                }
            }
            //case WAITING_FOR_INPUT_INSTITUTE -> {
                //answer =

            //}

            case WAITING_FOR_COMMAND -> answer = readCommand(messageText, chatId);

        }

        return answer;
    }


    public String readCommand(String command, long chatID) {
        switch (command) {
            case "/start" -> {
                startCommand.changeState(chatID);
                return startCommand.getBotText();
            }
            case "/help" -> {
                return helpCommand.getBotCommand();
            }
            case "/list" -> {
                return listCommand.getAllAvailableTrips();
            }
//            case "/edit" ->{
//
//            }
            default -> {
                if (String.valueOf(command.charAt(0)).equals("/")) return "Не удалось разпознать команду";
                else {
                    return "Ваше сообщение: " + command;
                }
            }
        }
    }
}


