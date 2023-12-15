package com.urfu.tgbot.command;

import com.urfu.tgbot.service.StateService;
import org.junit.jupiter.api.Test;

import static com.urfu.tgbot.enums.State.WAITING_FOR_INPUT_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Модульные тесты для класса `StartCommand`.
 */
public class StartCommandTest {

    /**
     * Проверяет, что метод `GetBotText()` отправляет нужный текст.
     */
    @Test
    public void testGetBotText() {
        StartCommand startCommand = new StartCommand(null);
        String expectedText = """
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """;
        String actualText = startCommand.getBotText();
        assertEquals(expectedText, actualText);
    }


    /**
     * Проверяет, что метод `ChangeState()` корректно меняет состояние чата.
     */
    @Test
    public void testChangeState() {
        StateService stateService = mock(StateService.class);
        StartCommand startCommand = new StartCommand(stateService);
        startCommand.changeState(123456L);
        verify(stateService).saveState(123456L, WAITING_FOR_INPUT_NAME);
    }
}
