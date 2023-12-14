package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Этот класс управляет обработкой пользовательских команд в Telegram-боте.
 */
@Component
@Controller
public class CommandManager {

    private final StartCommand startCommand;
    private final HelpCommand helpCommand;

    private final StateService stateService;

    private final NameEditor nameEditor;

    private final EditCommand editCommand;

    @Autowired
    public CommandManager(StartCommand startCommand, HelpCommand helpCommand, StateService stateService, NameEditor nameEditor, EditCommand editCommand) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
        this.stateService = stateService;
        this.nameEditor = nameEditor;
        this.editCommand = editCommand;
    }

    /**
     * Обрабатывает вводимые пользователем данные и реагирует соответствующим образом в зависимости от текущего состояния.
     *
     * @param messageText вводимое пользователем сообщение
     * @param chatId идентификатор чата пользователя
     * @return ответ бота на вводимые пользователем данные
     */
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
            case WAITING_FOR_INPUT_EDIT_CONFIRMATION -> answer = editCommand.handleConfirmInput(messageText, chatId);

            case WAITING_FOR_COMMAND -> answer = readCommand(messageText, chatId);
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
    public String readCommand(String command, long chatID) {
        switch (command) {
            case "/start" -> {
                startCommand.changeState(chatID);
                return startCommand.getBotText();
            }
            case "/help" -> {
                return helpCommand.getBotCommand();
            }
            case "/edit" -> {
                editCommand.updateState(chatID);
                return editCommand.getBotText(chatID);
            }
            default -> {
                return "Не удалось разпознать команду";
            }
        }
    }
}


