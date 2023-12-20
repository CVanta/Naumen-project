package com.urfu.tgbot.command;

import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void testCheckDateFormat() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkDateFormat("29-12-23 08:30");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkDateFormat("31/12/2023 12:00"));
    }

    @Test
    public void testCheckPlaces() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkPlaces("5");
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkPlaces("10"));
        assertThrows(NumberFormatException.class, () -> inputHandler.checkPlaces("abc"));
    }
}
