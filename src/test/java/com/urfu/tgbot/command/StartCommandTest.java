package com.urfu.tgbot.command;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Модульные тесты для класса `StartCommand`.
 */
public class StartCommandTest {

    /**
     * Проверяет, что метод `GetBotText()` отправляет нужный текст.
     */
    @Test
    public void testGetBotText() {
        StartCommand startCommand = new StartCommand();
        String expectedText = """
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """;
        String actualText = startCommand.getBotText();
        assertEquals(expectedText, actualText);
    }
}
