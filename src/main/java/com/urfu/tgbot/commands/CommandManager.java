package com.urfu.tgbot.commands;

import com.urfu.tgbot.botLogic.Keyboard;
import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.services.StateService;
import org.checkerframework.checker.units.qual.K;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Component
@Controller
public class CommandManager {

    private final StartCommand startCommand;

    private final HelpCommand helpCommand;

    private final ListCommand listCommand;

    private final StateService stateService;

    private final AddNewUser addNewUser;

    private final EditCommand editCommand;

    private final AddCommand addCommand;

    private final AddingTrip addingTrip;

    private final SignUpCommand signUpCommand;

    private final ProfileCommand profileCommand;

    private final ViewCommand viewCommand;

    private final ShowCommand showCommand;

    private final DelTripCommand delTripCommand;

    private final DelCommand delCommand;

    private final ReplyKeyboardMarkup emptyKeyboard = null;

    private final CsvCommand csvCommand;

    @Autowired
    public CommandManager(StartCommand startCommand, HelpCommand helpCommand, ListCommand listCommand,
                          StateService stateService, AddNewUser addNewUser, EditCommand editCommand,
                          AddCommand addCommand, AddingTrip addingTrip, SignUpCommand signUpCommand,
                          ProfileCommand profileCommand, ViewCommand viewCommand, ShowCommand showCommand,
                          DelTripCommand delTripCommand, DelCommand delCommand, CsvCommand csvCommand) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
        this.listCommand = listCommand;
        this.stateService = stateService;
        this.addNewUser = addNewUser;
        this.editCommand = editCommand;
        this.addCommand = addCommand;
        this.addingTrip = addingTrip;
        this.signUpCommand = signUpCommand;
        this.profileCommand = profileCommand;
        this.viewCommand = viewCommand;
        this.showCommand = showCommand;
        this.delTripCommand = delTripCommand;
        this.delCommand = delCommand;
        this.csvCommand = csvCommand;
    }


    public Pair<String, ReplyKeyboardMarkup> readInput(String messageText, long chatId, String username) {
        States state = stateService.getState(chatId);
        String answer = messageText;
        Keyboard keyboard =  new Keyboard();
        if (state == null){
            startCommand.changeState(chatId);
            String answerText = startCommand.getBotText();
            return Pair.of(answerText, emptyKeyboard);
        }
        else{
            switch (state) {
                case WAITING_FOR_INPUT_NAME -> {
                    try {
                        answer = addNewUser.editName(chatId, messageText, username);
                    } catch (Exception e) {
                        return Pair.of("Вы не изменили имя", emptyKeyboard);
                    }
                }
                case WAITING_FOR_INPUT_INSTITUTE -> {
                    try {
                        answer = addNewUser.editInstitute(chatId, messageText);
                    } catch (Exception e) {
                        return Pair.of("Вы не изменили институт", emptyKeyboard);
                    }
                }
                case WAITING_FOR_INPUT_PHONE_NUMBER -> {
                    try {
                        answer = addNewUser.editPhoneNumber(chatId, messageText);
                    } catch (Exception e) {
                        return Pair.of("Вы не изменили номер телефона", emptyKeyboard);
                    }
                }
                case WAITING_FOR_INPUT_TIME -> {
                    answer = addingTrip.addTimeTrip(chatId, messageText);
                    if (stateService.getState(chatId) == States.WAITING_FOR_INPUT_TIME){
                        return Pair.of(answer, emptyKeyboard);
                    }
                    return Pair.of(answer, keyboard.getNumbers1to9Keyboard());
                }

                case WAITING_FOR_INPUT_PLACES -> {
                    answer = addingTrip.addPlacesTrip(chatId, messageText);
                    return Pair.of(answer, keyboard.getDefaultCommandKeyboard());
                }

                case WAITING_FOR_INPUT_DESTINATION -> {
                    return Pair.of(addingTrip.addDriverDestinationTrip(chatId, messageText), emptyKeyboard);

                }
                case WAITING_FOR_INPUT_TRIP_NUMBER -> {
                    if (messageText.startsWith("0")) {
                        stateService.updateState(chatId, States.WAITING_FOR_COMMAND);
                        return Pair.of("Вы вышли", keyboard.getDefaultCommandKeyboard());
                    }
                    if (messageText.startsWith("/signUp")) {
                        int number;
                        try {
                            number = Integer.parseInt(messageText.split(" ")[1]);
                        } catch (Exception exception) {
                            return Pair.of("Введите номер поездки(ЦИФРОЙ!).", signUpCommand.getTripNumbersKeyboard());
                        }
                        answer = signUpCommand.registerUser(number, chatId);
                        if (stateService.getState(chatId) == States.WAITING_FOR_INPUT_TRIP_NUMBER)
                            return Pair.of(answer, signUpCommand.getTripNumbersKeyboard());
                        return Pair.of(answer, keyboard.getDefaultCommandKeyboard());
                    }
                    return Pair.of("Напишите /signUp {номер поездки}, для записи на поездку или 0 для выхода", signUpCommand.getTripNumbersKeyboard());
                }
                case WAITING_FOR_INPUT_EDIT_CONFIRMATION -> {
                    answer = editCommand.handleConfirmInput(messageText, chatId);
                    if(stateService.getState(chatId) ==  States.WAITING_FOR_INPUT_EDIT_CONFIRMATION)
                        return Pair.of(answer, keyboard.getYesNoKeyboard());
                    if (stateService.getState(chatId) ==  States.WAITING_FOR_INPUT_NAME)
                        return Pair.of(answer, emptyKeyboard);
                    return Pair.of(answer, keyboard.getDefaultCommandKeyboard());
                }
                case WAITING_FOR_INPUT_SHOW_OR_DEL -> {
                    if (messageText.startsWith("0")) {
                        stateService.updateState(chatId, States.WAITING_FOR_COMMAND);
                        return Pair.of("Вы вышли", keyboard.getDefaultCommandKeyboard());
                    }
                    if (messageText.startsWith("/show")) {
                        int number;
                        try {
                            number = Integer.parseInt(messageText.split(" ")[1]);
                        } catch (Exception exception) {
                            return Pair.of("Введите номер поездки(ЦИФРОЙ!).", viewCommand.getViewKeyboard(chatId));
                        }
                        showCommand.changeState(chatId);
                        return Pair.of(showCommand.getBotText(chatId, number), keyboard.getDefaultCommandKeyboard());
                    }
                    if(messageText.startsWith("/del")){
                        int number;
                        try {
                            number = Integer.parseInt(messageText.split(" ")[1]);
                        } catch (Exception exception) {
                            return Pair.of("Введите номер поездки(ЦИФРОЙ!).", viewCommand.getViewKeyboard(chatId));
                        }
                        delTripCommand.changeState(chatId);
                        return Pair.of(delTripCommand.delTrip(chatId, number), keyboard.getDefaultCommandKeyboard());
                    }
                    else
                    {
                        return Pair.of("Введенное сообщение не соответствует формату ввода", viewCommand.getViewKeyboard(chatId));
                    }
                }
                case WAITING_FOR_INPUT_DEL -> {
                    if (messageText.startsWith("0")) {
                        stateService.updateState(chatId, States.WAITING_FOR_COMMAND);
                        return Pair.of("Вы вышли", keyboard.getDefaultCommandKeyboard());
                    }
                    int number;
                    try {
                        number = Integer.parseInt(messageText.split(" ")[1]);
                    } catch (Exception exception) {
                        return Pair.of("Введите номер поездки(ЦИФРОЙ!).", delCommand.getDelKeyboard(chatId));
                    }
                    delCommand.changeState(chatId);
                    return Pair.of(delCommand.delTrip(chatId, number), keyboard.getDefaultCommandKeyboard());
                }
                case WAITING_FOR_COMMAND -> {
                    return readCommand(messageText, chatId, username);
                }
            }
        }

        return Pair.of(answer, keyboard.getDefaultCommandKeyboard());
    }


    public Pair<String, ReplyKeyboardMarkup> readCommand(String command, long chatID, String username) {
        Keyboard keyboard =  new Keyboard();
        command = command.split(" ")[0];
        switch (command) {
            case "/start" -> {
                startCommand.changeState(chatID);
                return Pair.of(startCommand.getBotText(), emptyKeyboard);
            }
            case "/help" -> {
                ReplyKeyboardMarkup defaultCommandKeyboard = keyboard.getDefaultCommandKeyboard();
                String answer = helpCommand.getBotCommand();
                return Pair.of(answer, defaultCommandKeyboard);
            }
            case "/list" -> {
                listCommand.changeState(chatID);
                return Pair.of(listCommand.getAllAvailableTrips(), signUpCommand.getTripNumbersKeyboard());
            }
            case "/edit" -> {
                editCommand.updateState(chatID);
                return Pair.of(editCommand.getBotText(chatID), keyboard.getYesNoKeyboard());
            }
            case "/add" -> {
                addCommand.updateState(chatID);
                return Pair.of(addCommand.getBotText(), emptyKeyboard);
            }
            case "/profile" -> {
                profileCommand.changeState(chatID);
                return Pair.of(profileCommand.viewTrips(chatID), delCommand.getDelKeyboard(chatID));
            }
            case "/csv" ->{
                csvCommand.generateAndSendCsv(chatID);
                return Pair.of("", keyboard.getDefaultCommandKeyboard());
            }
            case "/view" -> {
                viewCommand.changeState(chatID);
                String answer = viewCommand.getBotText(chatID);
                return Pair.of(answer, viewCommand.getViewKeyboard(chatID));
            }
            default -> {
                ReplyKeyboardMarkup replyKeyboardMarkup = keyboard.getDefaultCommandKeyboard();
                String answer = "Не удалось распознать команду";
                return  Pair.of(answer, replyKeyboardMarkup);
            }
        }
    }
}