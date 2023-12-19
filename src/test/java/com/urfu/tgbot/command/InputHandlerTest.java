package com.urfu.tgbot.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> inputHandler.checkFullName("Иванов Иван"));
        Assertions.assertEquals("Ввод должен содержать три слова", exception.getMessage());
    }


    /**
     * Проверяет, что метод checkFullName выбрасывает исключение IllegalArgumentException,
     * если не каждое слово начинается с заглавной буквы.
     */
    @Test
    public void testCheckFullNameCapitalLetters() {
        InputHandler inputHandler = new InputHandler();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkFullName("иванов Иван Иванович"));
        Assertions.assertEquals("Каждое слово должно начинаться с заглавной буквы", exception.getMessage());
    }

    /**
     * Проверяет, что метод checkFullName выбрасывает исключение IllegalArgumentException,
     * если входная строка содержит нелегальные символы, не являющиеся русскими буквами.
     */
    @Test
    public void testCheckFullNameRussianLetters() {
        InputHandler inputHandler = new InputHandler();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkFullName("Ivanov Ivan Ivanovich"));
        Assertions.assertEquals("Каждое слово должно состоять из русских букв", exception.getMessage());
    }


    /**
     * Проверяет, что метод checkPhoneNumber выбрасывает исключение IllegalArgumentException,
     * если номер телефона состоит из недопустимого количества цифр или не начинается с 7.
     */
    @Test
    public void testCheckPhoneNumberLengthAndFormat() {
        InputHandler inputHandler = new InputHandler();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkPhoneNumber("1234567890"));
        Assertions.assertEquals("Номер должен состоять из 11 цифр и начинаться с 7", exception.getMessage());
    }


    /**
     * Проверяет, что метод checkPhoneNumber выбрасывает исключение IllegalArgumentException,
     * если номер телефона содержит символы, отличные от цифр.
     */
    @Test
    public void testCheckPhoneNumberDigitsOnly() {
        InputHandler inputHandler = new InputHandler();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkPhoneNumber("721313a7890"));
        Assertions.assertEquals("Номер должен содержать только цифры", exception.getMessage());
    }


    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException
     * при использовании полного названия института вместо аббревиатуры.
     */
    @Test
    public void testCheckInstituteFullName() {
        InputHandler inputHandler = new InputHandler();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkInstitute("Институт естественных наук и математики"));
        Assertions.assertEquals("Введите название института аббревиатурой", exception.getMessage());
    }

    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException,
     * если входная строка является пустой.
     */
    @Test
    public void testCheckInstituteEmptyValue() {
        InputHandler inputHandler = new InputHandler();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkInstitute(" "));
        Assertions.assertEquals("Введенное значение не может быть пустым", exception.getMessage());
    }

    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException,
     * если входная строка содержит символы, отличные от русских букв.
     */
    @Test
    public void testCheckInstituteRussianLetters() {
        InputHandler inputHandler = new InputHandler();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkInstitute("ugi"));
        Assertions.assertEquals("Введите название института русскими буквами", exception.getMessage());
    }

    /**
     * Проверяет, что метод `check Yes No()` правильно проверяет ввод данных yes/no.
     */
    @Test
    public void testCheckYesNo() {
        InputHandler inputHandler = new InputHandler();
        assertDoesNotThrow(() -> inputHandler.checkYesNo("Нет"));
        assertDoesNotThrow(() -> inputHandler.checkYesNo("Да"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkYesNo("No"));
        Assertions.assertEquals("Строка должна быть 'Да' или 'Нет'", exception.getMessage());
    }
}
