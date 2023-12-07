package com.urfu.tgbot.commands;

import com.urfu.tgbot.commands.EditCommand;
import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*; // Для использования Mockito

public class EditCommandTest {
    private StateService stateService;
    private UserService userService;
    private EditCommand editCommand;

    @BeforeEach
    public void setUp() {
        stateService = mock(StateService.class);
        userService = mock(UserService.class);
        editCommand = new EditCommand(stateService, userService);
    }

    @Test
    public void testUpdateState() {
        long chatID = 123456L;
        editCommand.updateState(chatID);
        verify(stateService).updateState(chatID, States.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
    }

    @Test
    public void testGetBotText() {
        long chatID = 123456L;
        User user = new User(111);
        when(userService.getUserByChatID(chatID)).thenReturn(user);

        String expectedBotText = """
                В данный момент ваш профиль выглядит так:
                """ + user.getFormattedString() + """
                 
                 Вы уверены что хотите изменить его? 
                (введите "Да" или "Нет")""";
        String actualBotText = editCommand.getBotText(chatID);

        assertEquals(expectedBotText, actualBotText);
    }

    @Test
    public void testHandleConfirmInputYes() {
        long chatID = 123456L;
        String input = "Да";

        String expectedResponse = "Введите новое имя:";
        String actualResponse = editCommand.handleConfirmInput(input, chatID);

        verify(stateService).updateState(chatID, States.WAITING_FOR_INPUT_NAME);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testHandleConfirmInputNo() {
        long chatID = 123456L;
        String input = "Нет";

        String expectedResponse = "Бот ожидает следующей команды";
        String actualResponse = editCommand.handleConfirmInput(input, chatID);

        verify(stateService).updateState(chatID, States.WAITING_FOR_COMMAND);
        assertEquals(expectedResponse, actualResponse);
    }
}