package com.urfu.tgbot.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class InputHandlerTest {

    private final InputHandler inputHandler = new InputHandler();

    /**
     * Проверяет, что метод checkFullName выбрасывает исключение IllegalArgumentException
     * при недостаточном количестве слов во входной строке.
     */
    @Test
    public void testCheckFullNameWordCount() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkFullName("Иванов Иван"));
        Assertions.assertEquals("Ввод должен содержать три слова", exception.getMessage());
    }


    /**
     * Проверяет, что метод checkFullName выбрасывает исключение IllegalArgumentException,
     * если не каждое слово начинается с заглавной буквы.
     */
    @Test
    public void testCheckFullNameCapitalLetters() {
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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkPhoneNumber("721313a7890"));
        assertEquals("Номер должен содержать только цифры", exception.getMessage());
    }


    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException
     * при использовании полного названия института вместо аббревиатуры.
     */
    @Test
    public void testCheckInstituteFullName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkInstitute("Институт естественных наук и математики"));
        assertEquals("Введите название института аббревиатурой", exception.getMessage());
    }

    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException,
     * если входная строка является пустой.
     */
    @Test
    public void testCheckInstituteEmptyValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkInstitute(" "));
        assertEquals("Введенное значение не может быть пустым", exception.getMessage());
    }

    /**
     * Проверяет, что метод checkInstitute выбрасывает исключение IllegalArgumentException,
     * если входная строка содержит символы, отличные от русских букв.
     */
    @Test
    public void testCheckInstituteRussianLetters() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkInstitute("ugi"));
        assertEquals("Введенное значение не соответствует формату института", exception.getMessage());
    }

    /**
     * Проверяет, что метод `check Yes No()` правильно проверяет ввод данных yes/no.
     */
    @Test
    public void testCheckYesNo() {
        assertDoesNotThrow(() -> inputHandler.checkYesNo("Нет"));
        assertDoesNotThrow(() -> inputHandler.checkYesNo("Да"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkYesNo("No"));
        Assertions.assertEquals("Строка должна быть 'Да' или 'Нет'", exception.getMessage());
    }

    /**
     * Проверяет формат даты и времени. Убеждается, что метод checkDateFormat выбрасывает исключение
     * IllegalArgumentException, если формат даты и времени неверный.
     */

    @Test
    public void testCheckDateFormat() {
        InputHandler inputHandler = new InputHandler();
        assertDoesNotThrow(() -> inputHandler.checkDateFormat("29-12-23 08:30"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkDateFormat("31/12/2023 12:00"));
        Assertions.assertEquals("Неверный формат даты и времени", exception.getMessage());
    }

    /**
     * Проверяет, что метод checkDateFormat выбрасывает исключение IllegalArgumentException,
     * если указанная дата находится в прошлом.
     */
    @Test
    public void testCheckDateFormatWithDateInPast() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkDateFormat("31-12-1970 12:00"));
        Assertions.assertEquals("Невозможно создать поездку в прошлом", exception.getMessage());
    }

    /**
     * Проверяет валидацию количества мест. Убеждается, что метод checkPlaces выбрасывает исключение
     * IllegalArgumentException, если количество мест больше 10 или меньше 1.
     */

    @Test
    public void testCheckPlaces() {
        assertDoesNotThrow(() -> inputHandler.checkPlaces("5"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkPlaces("10"));
        Assertions.assertEquals("Число должно быть меньше 10 и больше ноля", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkPlaces("0"));
        Assertions.assertEquals("Число должно быть меньше 10 и больше ноля", exception.getMessage());
    }

    /**
     * Проверяет метод checkNumberBetween и убеждается, что он выбрасывает исключение,
     * если переданное число меньше или равно 0.
     */

    @Test
    public void testCheckPlacesWithLetters() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inputHandler.checkPlaces("1a"));
        Assertions.assertEquals("Невозможно преобразовать в число", exception.getMessage());
    }

    /**
     * Проверяет метод checkNumberBetween и убеждается, что он выбрасывает исключение,
     * если переданное число, больше заданного опорного значения.
     */


    @Test
    public void testCheckNumberBetween_ThrowsExceptionWhenNumberToCheckIsZero() {
        assertDoesNotThrow(() -> inputHandler.checkNumberBetween(1, 1));
        Exception exception = assertThrows(Exception.class,
                () -> inputHandler.checkNumberBetween(0, 10));
        assertEquals("Не сущетсвует поездки с таким номером, выберите поездку из списка.",
                exception.getMessage());
    }

    /**
     * Проверяет метод checkNumberBetween и убеждается, что он выбрасывает исключение
     * при передаче числа, которое больше заданного опорного значения. Проверяет соответствие сообщения ошибки.
     */

    @Test
    public void testCheckNumberBetweenWithGreaterNumber() {
        Exception exception = assertThrows(Exception.class,
                () -> inputHandler.checkNumberBetween(12, 10));
        assertEquals("Не сущетсвует поездки с таким номером, выберите поездку из списка.",
                exception.getMessage());
    }


}
