package com.urfu.tgbot.command;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartCommandTest {

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
