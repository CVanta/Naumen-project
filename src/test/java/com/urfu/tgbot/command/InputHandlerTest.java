package com.urfu.tgbot.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Модульные тесты для класса `InputHandler`.
 */
public class InputHandlerTest {

    /**
     * Проверяет, что метод checkFullName выбрасывает исключение IllegalArgumentException
     * при недостаточном количестве слов во входной строке.
     */
    @Test
    public void testCheckFullNameWordCount() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("Иванов Иван"));
        try {
            inputHandler.checkFullName("Иванов Иван");
        } catch (IllegalArgumentException exception) {
            assertEquals("Ввод должен содержать три слова", exception.getMessage());
        }
    }


    /**
     * Проверяет, что метод checkFullName выбрасывает исключение IllegalArgumentException,
     * если не каждое слово начинается с заглавной буквы.
     */
    @Test
    public void testCheckFullNameCapitalLetters() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("иванов Иван Иванович"));
        try {
            inputHandler.checkFullName("иванов Иван Иванович");
        } catch (IllegalArgumentException exception) {
            assertEquals("Каждое слово должно начинаться с заглавной буквы", exception.getMessage());
        }
    }

    /**
     * Проверяет, что метод checkFullName выбрасывает исключение IllegalArgumentException,
     * если входная строка содержит нелегальные символы, не являющиеся русскими буквами.
     */
    @Test
    public void testCheckFullNameRussianLetters() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("Ivanov Ivan Ivanovich"));
        try {
            inputHandler.checkFullName("Ivanov Ivan Ivanovich");
        } catch (IllegalArgumentException exception) {
            assertEquals("Каждое слово должно состоять из русских букв", exception.getMessage());
        }
    }


    /**
     * Проверяет, что метод checkPhoneNumber выбрасывает исключение IllegalArgumentException,
     * если номер телефона состоит из недопустимого количества цифр или не начинается с 7.
     */
    @Test
    public void testCheckPhoneNumberLengthAndFormat() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkPhoneNumber("1234567890"));
        try {
            inputHandler.checkPhoneNumber("711");
        } catch (Exception exception) {
            assertEquals("Номер должен состоять из 11 цифр и начинаться с 7", exception.getMessage());
        }
    }

    /**
     * Проверяет, что метод checkPhoneNumber выбрасывает исключение IllegalArgumentException,
     * если номер телефона содержит символы, отличные от цифр.
     */
    @Test
    public void testCheckPhoneNumberDigitsOnly() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkPhoneNumber("721313a"));
        try {
            inputHandler.checkPhoneNumber("7123456789a");
        } catch (Exception exception) {
            assertEquals("Номер должен содержать только цифры", exception.getMessage());
        }
    }


    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException
     * при использовании полного названия института вместо аббревиатуры.
     */
    @Test
    public void testCheckInstituteFullName() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkInstitute("Институт естественных наук и математики"));
        try {
            inputHandler.checkInstitute("Институт естественных наук и математики");
        } catch (IllegalArgumentException exception) {
            assertEquals("Введите название института абревиатурой", exception.getMessage());
        }
    }

    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException,
     * если входная строка является пустой.
     */
    @Test
    public void testCheckInstituteEmptyValue() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkInstitute(" "));
        try {
            inputHandler.checkInstitute(" ");
        } catch (IllegalArgumentException exception) {
            assertEquals("Введенное значение не может быть пустым", exception.getMessage());
        }
    }

    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException,
     * если входная строка содержит символы, отличные от русских букв.
     */
    @Test
    public void testCheckInstituteRussianLetters() {
        InputHandler inputHandler = new InputHandler();
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkInstitute("aboba"));
        try {
            inputHandler.checkInstitute("aboba");
        } catch (IllegalArgumentException exception) {
            assertEquals("Введите название института русскими буквами", exception.getMessage());
        }
    }

    /**
     * Проверяет, что метод `check Yes No()` правильно проверяет ввод данных yes/no.
     */
    @Test
    public void testCheckYesNo() {
        InputHandler inputHandler = new InputHandler();
        assertDoesNotThrow(() -> inputHandler.checkYesNo("Нет"));
        assertDoesNotThrow(() -> inputHandler.checkYesNo("Да"));
        assertThrows(IllegalArgumentException.class, () -> inputHandler.checkYesNo("No"));
        try {
            inputHandler.checkYesNo("No");
        } catch (Exception exception) {
            assertEquals("Строка должна быть 'Да' или 'Нет'", exception.getMessage());
        }
    }
}
