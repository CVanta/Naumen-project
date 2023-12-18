package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.service.StateService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandManagerTest {

    @Mock
    private StartCommand startCommand;

    @Mock
    private HelpCommand helpCommand;

    @Mock
    private StateService stateService;

    @Mock
    private NameEditor nameEditor;

    @Mock
    private EditCommand editCommand;
    @InjectMocks
    private CommandManager commandManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commandManager = new CommandManager(startCommand, helpCommand, stateService, nameEditor, editCommand);
    }

    @Test
    public void testHandleInputUpdateStateStartCommandStateNull() {
        long chatId = 123;
        String messageText = "/start";
        when(stateService.getState(chatId)).thenReturn(null);
        when(startCommand.getBotText()).thenReturn("""
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """);
        String response = commandManager.handleInputUpdateState(messageText, chatId);
        verify(stateService).updateState(chatId, StateEnum.WAITING_FOR_INPUT_NAME);
        assertEquals("""
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """, response);
    }

    @Test
    public void testHandleInputUpdateStateWaitingForInputName() throws Exception {
        long chatId = 123;
        String messageText = "Коновалов Тимур Артёмович";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_INPUT_NAME);
        when(nameEditor.editName(chatId, messageText)).thenReturn("Введите номер телефона");
        String response = commandManager.handleInputUpdateState(messageText, chatId);
        verify(nameEditor).editName(chatId, messageText);
        assertEquals("Введите номер телефона", response);
    }

    @Test
    public void testHandleInputUpdateStateWaitingForInputEditConfirmation() {
        long chatId = 123;
        String messageText = "Да";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
        when(editCommand.handleConfirmInput(messageText, chatId)).thenReturn("Введите новое ФИО");
        String response = commandManager.handleInputUpdateState(messageText, chatId);
        verify(editCommand).handleConfirmInput(messageText, chatId);
        assertEquals("Введите новое ФИО", response);
    }

    @Test
    public void testHandleCommandStartCommand() {
        long chatId = 123;
        when(startCommand.getBotText()).thenReturn("""
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """);
        String response = commandManager.handleInputUpdateState("/start", chatId);
        verify(stateService).updateState(chatId, StateEnum.WAITING_FOR_INPUT_NAME);
        assertEquals("""
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """, response);
    }

    @Test
    public void testHandleCommandUnrecognizedCommand() {
        long chatId = 123;
        String messageText = "InvalidCommand";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_COMMAND);
        String response = commandManager.handleInputUpdateState(messageText, chatId);
        assertEquals("Не удалось распознать команду", response);
    }

    @Test
    public void testHandleCommandEditCommand() {
        long chatId = 123;
        String messageText = "/edit";
        when(stateService.getState(chatId)).thenReturn(StateEnum.WAITING_FOR_COMMAND);
        when(editCommand.getBotText(chatId)).thenReturn("Введите новое ФИО");
        String response = commandManager.handleInputUpdateState(messageText, chatId);
        verify(stateService).updateState(chatId, StateEnum.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
        assertEquals("Введите новое ФИО", response);
    }

}