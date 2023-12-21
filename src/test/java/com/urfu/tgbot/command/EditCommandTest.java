package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Модульные тесты для класса `Edit Command`.
 */
@ExtendWith(MockitoExtension.class)
public class EditCommandTest {
    @Mock
    private StateService stateService;

    @Mock
    private UserService userService;
    @InjectMocks
    private EditCommand editCommand;

    private final long chatID = 123456;


    /**
     * Проверяет, что метод `getBotText()` корректно генерирует текст бота.
     */
    @Test
    public void testGetBotText() {
        User user = new User(chatID);
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
        String actualResponse = editCommand.handleConfirmInput("Да", chatID);
        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_INPUT_NAME);
        assertEquals("Введите новое ФИО", actualResponse);
    }

    /**
     * Проверяет, что метод `handle Confirm Input()` корректно обрабатывает вводимые пользователем данные для
     * отклонения запроса на редактирование.
     */
    @Test
    public void testHandleConfirmInputNo() {
        String actualResponse = editCommand.handleConfirmInput("Нет", chatID);
        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
        assertEquals("Бот ожидает следующей команды", actualResponse);
    }
}