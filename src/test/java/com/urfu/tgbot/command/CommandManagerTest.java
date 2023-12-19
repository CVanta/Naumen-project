package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.service.StateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandManagerTest {

    @Mock
    private StartCommand startCommand;

    @Mock
    private StateService stateService;

    @Mock
    private NameEditor nameEditor;

    @Mock
    private EditCommand editCommand;
    @InjectMocks
    private CommandManager commandManager;

    private final long chatId = 123;


    /**
     * Проверяет обновление состояния при получении команды /start, когда состояние равно null.
     */
    @Test
    public void testHandleInputUpdateStateStartCommandStateNull() {
        when(stateService.getState(chatId)).thenReturn(null);
        when(startCommand.getBotText()).thenReturn("""
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """);
        commandManager.handleInputUpdateState("/start", chatId);
        verify(stateService).updateState(chatId, StateEnum.WAITING_FOR_INPUT_NAME);
    }

    /**
     * Проверяет обработку ввода в состоянии ожидания ввода ФИО.
     */
    @Test
    public void testHandleInputUpdateStateWaitingForInputName() {

        String messageText = "Коновалов Тимур Артёмович";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_INPUT_NAME);
        when(nameEditor.editName(chatId, messageText)).thenReturn("Введите номер телефона");
        commandManager.handleInputUpdateState(messageText, chatId);
        verify(nameEditor).editName(chatId, messageText);
    }

    /**
     * Проверяет обработку ввода в состоянии ожидания подтверждения редактирования.
     */
    @Test
    public void testHandleInputUpdateStateWaitingForInputEditConfirmation() {
        String messageText = "Да";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
        when(editCommand.handleConfirmInput(messageText, chatId)).thenReturn("Введите новое ФИО");
        commandManager.handleInputUpdateState(messageText, chatId);
        verify(editCommand).handleConfirmInput(messageText, chatId);
    }

    /**
     * Проверяет обработку команды /start.
     */
    @Test
    public void testHandleCommandStartCommand() {
        when(startCommand.getBotText()).thenReturn("""
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """);
        commandManager.handleInputUpdateState("/start", chatId);
        verify(stateService).updateState(chatId, StateEnum.WAITING_FOR_INPUT_NAME);
    }

    /**
     * Проверяет обработку неcуществуюшей команды.
     */
    @Test
    public void testHandleCommandUnrecognizedCommand() {
        String messageText = "InvalidCommand";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_COMMAND);
        String response = commandManager.handleInputUpdateState(messageText, chatId);
        assertEquals("Не удалось распознать команду", response);
    }

    /**
     * Проверяет обработку команды /edit.
     */
    @Test
    public void testHandleCommandEditCommand() {
        String messageText = "/edit";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_COMMAND);
        when(editCommand.getBotText(chatId)).thenReturn("Введите новое ФИО");
        commandManager.handleInputUpdateState(messageText, chatId);
        verify(stateService).updateState(chatId, StateEnum.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
    }

}