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

    private final EditCommand editCommand;

    private final AddCommand addCommand;

    private final AddingTrip addingTrip;

    private final SignUpCommand signUpCommand;

    private final ViewCommand viewCommand;

    @Autowired
    public CommandManager(StartCommand startCommand, HelpCommand helpCommand, ListCommand listCommand, StateService stateService, NameEditor nameEditor, EditCommand editCommand, AddCommand addCommand, AddingTrip addingTrip, SignUpCommand signUpCommand, ViewCommand viewCommand) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
        this.listCommand = listCommand;
        this.stateService = stateService;
        this.nameEditor = nameEditor;
        this.editCommand = editCommand;
        this.addCommand = addCommand;
        this.addingTrip = addingTrip;
        this.signUpCommand = signUpCommand;
        this.viewCommand = viewCommand;
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
            case WAITING_FOR_INPUT_INSTITUTE -> {
                try {
                    answer = nameEditor.editInstitute(chatId, messageText);
                } catch (Exception e) {
                    return "Вы не изменили институт";
                }
            }
            case WAITING_FOR_INPUT_PHONE_NUMBER -> {
                try {
                    answer = nameEditor.editPhoneNumber(chatId, messageText);
                } catch (Exception e) {
                    return "Вы не изменили номер телефона";
                }
            }
            case WAITING_FOR_INPUT_TIME -> {
                return addingTrip.addTimeTrip(chatId, messageText);
            }

            case WAITING_FOR_INPUT_PLACES -> {
                return addingTrip.addPlacesTrip(chatId, messageText);
            }

            case WAITING_FOR_INPUT_DESTINATION -> {
                return addingTrip.addDriverDestinationTrip(chatId, messageText);

            }
            case WAITING_FOR_INPUT_TRIP_NUMBER -> {
                int number;
                try {
                    number = Integer.parseInt(messageText);
                } catch (Exception exception) {
                    return "Введите номер поездки(ЦИФРОЙ!).";
                }
                signUpCommand.changeState(chatId);
                return signUpCommand.registerUser(number, chatId);
            }
            case WAITING_FOR_INPUT_EDIT_CONFIRMATION -> answer = editCommand.handleConfirmInput(messageText, chatId);

            case WAITING_FOR_COMMAND -> answer = readCommand(messageText, chatId);

        }

        return answer;
    }


    public String readCommand(String command, long chatID) {
        command = command.split(" ")[0];
        switch (command) {
            case "/start" -> {
                startCommand.changeState(chatID);
                return startCommand.getBotText();
            }
            case "/help" -> {
                return helpCommand.getBotCommand();
            }
            case "/list" -> {
                listCommand.changeState(chatID);
                return listCommand.getAllAvailableTrips();
            }
            case "/edit" -> {
                editCommand.updateState(chatID);
                return editCommand.getBotText(chatID);
            }
            case "/add" -> {
                addCommand.updateState(chatID);
                return addCommand.getBotText();
            }
            case "/view" -> {
                return viewCommand.viewTrips(chatID);
            }
            default -> {
                if (String.valueOf(command.charAt(0)).equals("/")) return "Не удалось разпознать команду";
                else {
                    return "Ваше сообщение: " + command;
                }
            }
        }
    }
}


