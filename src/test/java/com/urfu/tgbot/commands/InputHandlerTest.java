package com.urfu.tgbot.commands;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

/**
 * Модульные тесты для класса `InputHandler`.
 */
public class InputHandlerTest {

    /**
     * Проверяет, что метод `check Full Name()` правильно проверяет ввод полного имени.
     */
    @Test
    public void testCheckFullName() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkFullName("Иванов Иван Иванович");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("Иванов Иван"));
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("иванов Иван Иванович"));
    }

    /**
     * Проверяет, что метод `check Phone Number()` правильно проверяет ввод номера телефона.
     */
    @Test
    public void testCheckPhoneNumber() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkPhoneNumber("71234567890");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkPhoneNumber("1234567890"));
    }

    /**
     * Проверяет, что метод `chek institute()` правильно проверяет вводимые институтом данные.
     */
    @Test
    public void testCheckInstitute() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkInstitute("МГУ");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkInstitute("Национальный исследовательский университет "
                + "Высшая школа экономики"));
    }

    /**
     * Проверяет, что метод `check Yes No()` правильно проверяет ввод данных yes/no.
     */
    @Test
    public void testCheckYesNo() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkYesNo("Да");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkYesNo("No"));
    }
}
