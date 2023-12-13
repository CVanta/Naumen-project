package com.urfu.tgbot.commands;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class InputHandlerTest {

    @Test
    public void testCheckFullName() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkFullName("Иванов Иван Иванович");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("Иванов Иван"));
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("иванов Иван Иванович"));
    }

    @Test
    public void testCheckPhoneNumber() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkPhoneNumber("71234567890");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkPhoneNumber("1234567890"));

    }

    @Test
    public void testCheckInstitute() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkInstitute("МГУ");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkInstitute("Национальный исследовательский университет "
                + "Высшая школа экономики"));
    }

    @Test
    public void testCheckYesNo() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkYesNo("Да");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkYesNo("No"));
    }

}
