package com.urfu.tgbot.commands;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {

    @Test
    public void testGetBotCommand() {
        HelpCommand helpCommand = new HelpCommand();
        String expectedCommandList = "Список доступных команд:<\n/start - старт бота\n/edit - изменение профиля\n/help - список доступных команд";
        String actualCommandList = helpCommand.getBotCommand();
        assertEquals(expectedCommandList, actualCommandList);
    }
}