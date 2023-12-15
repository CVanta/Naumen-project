package com.urfu.tgbot.command;

/**
 * Класс для обработки входных данных, включая проверку ФИО, номера телефона, института и т.д.
 */
public class InputHandler {

    /**
     * Проверяет корректность ввода ФИО.
     *
     * @param name ФИО для проверки
     * @throws IllegalArgumentException если ФИО не соответствует заданным критериям
     */
    public void checkFullName(String name) {
        String[] words = name.split(" ");
        if (words.length != 3) {
            throw new IllegalArgumentException("Ввод должен содержать три слова");
        }

        for (String word : words) {
            if (!Character.isUpperCase(word.charAt(0))) {
                throw new IllegalArgumentException("Каждое слово должно начинаться с заглавной буквы");
            }
            if (!word.matches("^[А-ЯЁа-яё\\s]+$")) {
                throw new IllegalArgumentException("Каждое слово должно состоять из русских букв");
            }
        }
    }

    /**
     * Проверяет корректность ввода номера телефона.
     *
     * @param number Номер телефона для проверки
     * @throws IllegalArgumentException если номер телефона не соответствует заданным критериям
     */
    public void checkPhoneNumber(String number) {
        if (number.length() != 11 || !number.startsWith("7")) {
            throw new IllegalArgumentException("Номер должен состоять из 11 цифр и начинаться с 7");
        }
        if (!number.matches("[0-9]+")) {
            throw new IllegalArgumentException("Номер должен содержать только цифры");
        }
    }

    /**
     * Проверяет корректность ввода института.
     *
     * @param input Институт для проверки
     * @throws IllegalArgumentException если введенное значение не соответствует формату института
     */
    public void checkInstitute(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Введенное значение не может быть пустым");
        }
        if (input.length() > 10) {
            throw new IllegalArgumentException("Не бывает таких длинных институтов");
        }
        String regex = "^(?:[А-Яа-яЁё]+(?:\\s[А-Яа-яЁё]+)?)$";
        if (!input.matches(regex)) {
            throw new IllegalArgumentException("Введенное значение не соответствует формату института");
        }
    }


    /**
     * Проверяет, что полученная строка является "Да" или "Нет".
     *
     * @param input Строка для проверки
     * @throws IllegalArgumentException если строка не является "Да" или "Нет"
     */
    public void checkYesNo(String input) {
        if (!input.equalsIgnoreCase("Да") && !input.equalsIgnoreCase("Нет")) {
            throw new IllegalArgumentException("Строка должна быть 'Да' или 'Нет'");
        }
    }
}



