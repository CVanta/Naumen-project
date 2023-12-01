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

    private final ProfileCommand profileCommand;

    private final ViewCommand viewCommand;

    private final ShowCommand showCommand;

    @Autowired
    public CommandManager(StartCommand startCommand, HelpCommand helpCommand, ListCommand listCommand, StateService stateService, NameEditor nameEditor, EditCommand editCommand, AddCommand addCommand, AddingTrip addingTrip, SignUpCommand signUpCommand, ProfileCommand profileCommand, ViewCommand viewCommand, ShowCommand showCommand) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
        this.listCommand = listCommand;
        this.stateService = stateService;
        this.nameEditor = nameEditor;
        this.editCommand = editCommand;
        this.addCommand = addCommand;
        this.addingTrip = addingTrip;
        this.signUpCommand = signUpCommand;
        this.profileCommand = profileCommand;
        this.viewCommand = viewCommand;
        this.showCommand = showCommand;
    }


    public String readInput(String messageText, long chatId, String username) {
        States state = stateService.getState(chatId);
        String answer = messageText;
        if (state == null){
            startCommand.changeState(chatId);
            answer = startCommand.getBotText();
        }
        else{
            switch (state) {
                case WAITING_FOR_INPUT_NAME -> {
                    try {
                        answer = nameEditor.editName(chatId, messageText, username);
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
                    return signUpCommand.registerUser(number, chatId);
                }
                case WAITING_FOR_INPUT_EDIT_CONFIRMATION -> answer = editCommand.handleConfirmInput(messageText, chatId);
                case WAITING_FOR_INPUT_SHOW_OR_DEL -> {
                    if (messageText.startsWith("0")) {
                        stateService.updateState(chatId, States.WAITING_FOR_COMMAND);
                        return "Вы вышли";
                    } else if (messageText.startsWith("/show")) {
                        int number;
                        try {
                            number = Integer.parseInt(messageText.split(" ")[1]);
                        } catch (Exception exception) {
                            return "Введите номер поездки(ЦИФРОЙ!).";
                        }
                        showCommand.changeState(chatId);
                        return showCommand.getBotText(chatId, number);
                    }
                }

                case WAITING_FOR_COMMAND -> answer = readCommand(messageText, chatId, username);

            }
        }

        return answer;
    }


    public String readCommand(String command, long chatID, String username) {
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
            case "/profile" -> {
                return profileCommand.viewTrips(chatID);
            }
            case "/view" -> {
                viewCommand.changeState(chatID);
                return viewCommand.getBotText(chatID);
            }
            default -> {
                if (String.valueOf(command.charAt(0)).equals("/")) return "Не удалось разпознать команду";
                else {
                    return "Ваше сообщение -> " + command + " <-";
                }
            }
        }
    }
}