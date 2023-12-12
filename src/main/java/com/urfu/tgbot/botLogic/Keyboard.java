package com.urfu.tgbot.botLogic;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    public ReplyKeyboardMarkup getDefaultCommandKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        String[] commands = { "/help", "/edit", "/list", "/view", "/profile", "/add" };
        for (String command : commands) {
            row.add(command);
            keyboard.add(row);
            row = new KeyboardRow();
        }
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getNumbers1to9Keyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                row.add(Integer.toString(i*3 + j + 1));
            }
            keyboardRows.add(row);
            row = new KeyboardRow();
        }
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getTripNumbersKeyboard(int size) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < size; i++) {
            row.add("/signUp " + (i + 1));
            keyboardRows.add(row);
            row = new KeyboardRow();
        }
        row.add("0");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getYesNoKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Да");
        row.add("Нет");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getShowOrDelTripNumbers(int size) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < size; i++) {
            row.add("/show " + (i + 1));
            row.add("/del " + (i + 1));
            keyboardRows.add(row);
            row = new KeyboardRow();
        }
        row.add("0");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getDelNumbers(int size) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < size; i++) {
            row.add("/del " + (i + 1));
            keyboardRows.add(row);
            row = new KeyboardRow();
        }
        row.add("0");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }

}
