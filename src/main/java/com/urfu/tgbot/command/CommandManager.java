package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Этот класс управляет обработкой пользовательских команд в Telegram-боте.
 */
@Component
public class CommandManager {

    private final StartCommand startCommand;
    private final HelpCommand helpCommand;
    private final ListCommand listCommand;
    private final StateService stateService;
    private final NameEditor nameEditor;
    private final EditCommand editCommand;
    private final AddingTrip addingTrip;
    private final SignUpCommand signUpCommand;
    private final ProfileCommand profileCommand;
    private final ViewCommand viewCommand;
    private final ShowCommand showCommand;
    private final DelTripCommand delTripCommand;
    private final DelCommand delCommand;

    @Autowired
    public CommandManager(StartCommand startCommand, HelpCommand helpCommand, ListCommand listCommand,
                          StateService stateService, NameEditor nameEditor, EditCommand editCommand, AddingTrip addingTrip, SignUpCommand signUpCommand,
                          ProfileCommand profileCommand, ViewCommand viewCommand, ShowCommand showCommand,
                          DelTripCommand delTripCommand, DelCommand delCommand) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
        this.listCommand = listCommand;
        this.stateService = stateService;
        this.nameEditor = nameEditor;
        this.editCommand = editCommand;
        this.addingTrip = addingTrip;
        this.signUpCommand = signUpCommand;
        this.profileCommand = profileCommand;
        this.viewCommand = viewCommand;
        this.showCommand = showCommand;
        this.delTripCommand = delTripCommand;
        this.delCommand = delCommand;
    }

    /**
     * Обрабатывает вводимые пользователем данные и реагирует соответствующим образом в зависимости от текущего состояния.
     *
     * @param messageText вводимое пользователем сообщение
     * @param chatId идентификатор чата пользователя
     * @return ответ бота на вводимые пользователем данные
     */
    public String handleInputUpdateState(String messageText, long chatId) {
        StateEnum state = stateService.getState(chatId);
        String answer = " ";
        if (state == null){
            stateService.updateState(chatId, StateEnum.WAITING_FOR_INPUT_NAME);
            answer = startCommand.getBotText();
            return answer;
        }
        else{
            switch (state) {
                case WAITING_FOR_INPUT_NAME -> answer = nameEditor.editName(chatId, messageText);
                case WAITING_FOR_INPUT_INSTITUTE -> answer = nameEditor.editInstitute(chatId, messageText);
                case WAITING_FOR_INPUT_PHONE_NUMBER -> answer = nameEditor.editPhoneNumber(chatId, messageText);
                case WAITING_FOR_INPUT_TIME -> answer = addingTrip.addTimeTrip(chatId, messageText);

                case WAITING_FOR_INPUT_PLACES -> answer = addingTrip.addPlacesTrip(chatId, messageText);

                case WAITING_FOR_INPUT_DESTINATION -> answer = addingTrip.addDriverDestinationTrip(chatId, messageText);
                case WAITING_FOR_INPUT_TRIP_NUMBER -> {
                    if (messageText.startsWith("0")) {
                        stateService.updateState(chatId, StateEnum.WAITING_FOR_COMMAND);
                        return "Вы вышли";
                    }
                    if (messageText.startsWith("/signUp")) {
                        int number;
                        try {
                            number = Integer.parseInt(messageText.split(" ")[1]);
                        } catch (Exception exception) {
                            return "Введите номер поездки(ЦИФРОЙ!).";
                        }
                        return signUpCommand.registerUser(number, chatId);
                    }
                    return "Напишите /signUp {номер поездки}, для записи на поездку или 0 для выхода";
                }
                case WAITING_FOR_INPUT_EDIT_CONFIRMATION -> answer = editCommand.handleConfirmInput(messageText, chatId);
                case WAITING_FOR_INPUT_SHOW_OR_DEL -> {
                    if (messageText.startsWith("0")) {
                        stateService.updateState(chatId, StateEnum.WAITING_FOR_COMMAND);
                        return "Вы вышли";
                    }
                    if (messageText.startsWith("/show")) {
                        int number;
                        try {
                            number = Integer.parseInt(messageText.split(" ")[1]);
                        } catch (Exception exception) {
                            return "Введите номер поездки(ЦИФРОЙ!).";
                        }
                        stateService.updateState(chatId,StateEnum.WAITING_FOR_COMMAND);

                        return showCommand.getBotText(chatId, number);
                    }
                    if(messageText.startsWith("/del")){
                        int number;
                        try {
                            number = Integer.parseInt(messageText.split(" ")[1]);
                        } catch (Exception exception) {
                            return "Введите номер поездки(ЦИФРОЙ!).";
                        }
                        stateService.updateState(chatId, StateEnum.WAITING_FOR_COMMAND);
                        return delTripCommand.delTrip(chatId, number);
                    }
                }
                case WAITING_FOR_INPUT_DEL -> {
                    if (messageText.startsWith("0")) {
                        stateService.updateState(chatId, StateEnum.WAITING_FOR_COMMAND);
                        return "Вы вышли";
                    }
                    int number;
                    try {
                        number = Integer.parseInt(messageText.split(" ")[1]);
                    } catch (Exception exception) {
                        return "Введите номер поездки(ЦИФРОЙ!).";
                    }
                    stateService.updateState(chatId, StateEnum.WAITING_FOR_COMMAND);
                    return delCommand.delTrip(chatId, number);
                }
                case WAITING_FOR_COMMAND -> answer = handleCommand(messageText, chatId);
            }
        }
        return answer;
    }

    /**
     * Обрабатывает пользовательский ввод для определенной команды.
     *
     * @param command команда, введенная пользователем
     * @param chatID идентификатор чата пользователя
     * @return ответ бота на команду пользователя
     */
    public String handleCommand(String command, long chatID) {
        command = command.split(" ")[0];
        switch (command) {
            case "/start" -> {
                stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_NAME);
                return startCommand.getBotText();
            }
            case "/help" -> {
                return helpCommand.getBotCommand();
            }
            case "/list" -> {
                stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_TRIP_NUMBER);
                return listCommand.getAllAvailableTrips();
            }
            case "/edit" -> {
                stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
                return editCommand.getBotText(chatID);
            }
            case "/add" -> {
                try {
                    stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_DESTINATION);
                }
                catch (Exception e)
                {
                    stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_DESTINATION);
                }
                return "Введите место назначения";
            }
            case "/profile" -> {
                stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_DEL);
                return profileCommand.viewTrips(chatID);
            }
            case "/view" -> {
                stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_SHOW_OR_DEL);
                return viewCommand.getBotText(chatID);
            }
            default -> {
                return "Не удалось распознать команду.";
                }
            }
        }
    }