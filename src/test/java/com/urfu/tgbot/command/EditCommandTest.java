package com.urfu.tgbot.command;

import com.urfu.tgbot.enums.StateEnum;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Модульные тесты для класса `Edit Command`.
 */
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

    /**
     * Проверяет, что метод `updateState()` корректно обновляет состояние чата.
     */
    @Test
    public void testUpdateState() {
        long chatID = 123456L;
        //editCommand.updateState(chatID);
        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_INPUT_EDIT_CONFIRMATION);
    }

    /**
     * Проверяет, что метод `getBotText()` корректно генерирует текст бота.
     */
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

    /**
     * Проверяет, что метод `handle Confirm Input()` корректно обрабатывает пользовательский ввод для
     * подтверждения запроса на редактирование.
     */
    @Test
    public void testHandleConfirmInputYes() {
        long chatID = 123456L;
        String input = "Да";

        String expectedResponse = "Введите новое ФИО";
        String actualResponse = editCommand.handleConfirmInput(input, chatID);

        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_INPUT_NAME);
        assertEquals(expectedResponse, actualResponse);
    }

    /**
     * Проверяет, что метод `handle Confirm Input()` корректно обрабатывает вводимые пользователем данные для
     * отклонения запроса на редактирование.
     */
    @Test
    public void testHandleConfirmInputNo() {
        long chatID = 123456L;
        String input = "Нет";

        String expectedResponse = "Бот ожидает следующей команды";
        String actualResponse = editCommand.handleConfirmInput(input, chatID);

        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
        assertEquals(expectedResponse, actualResponse);
    }
}