package com.urfu.tgbot.commands;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {

    @Test
    public void testGetBotCommand() {
        HelpCommand helpCommand = new HelpCommand();
        String expectedCommandList = "Список доступных команд:<\n/start - старт бота\n/add - добавление поездки\n/edit - изменение профиля\n/help - помощь\n/list - вывод всех поездок\n/view - просмотр своих поездок\n/show - список пассажиров в поездке\n/profile - список поездок, на которые вы записаны\n";
        String actualCommandList = helpCommand.getBotCommand();
        assertEquals(expectedCommandList, actualCommandList);
    }
}