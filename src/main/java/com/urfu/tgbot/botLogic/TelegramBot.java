package com.urfu.tgbot.botLogic;

import com.urfu.tgbot.commands.CommandManager;
import com.urfu.tgbot.configs.BotConfig;
import org.graalvm.collections.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot implements MessageSender {
    private final BotConfig config;
    private final CommandManager commandManager;

    private ReplyKeyboardMarkup currentKeyboard;

    public TelegramBot(BotConfig config, CommandManager commandManager) {
        this.config = config;
        this.commandManager = commandManager;
    }

    /**
     * Возвращает имя пользователя бота.
     *
     * @return Имя пользователя бота.
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Возвращает токен бота.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    /**
     * Обрабатывает входящие обновления от API Telegram.
     *
     * @param update Объект обновления.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String username = update.getMessage().getFrom().getUserName();
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            Pair<String, ReplyKeyboardMarkup> pair = commandManager.readInput(messageText, chatID, username);
            this.currentKeyboard = pair.getRight();
            sendMessage(chatID, pair.getLeft());
        }
    }

    /**
     * Отправляет сообщение в указанный идентификатор чата.
     *
     * @param chatId     идентификатор чата.
     * @param textToSend Сообщение, которое нужно отправить.
     */
//    private void sendMessage(long chatId, String textToSend){
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(textToSend);
//        try {
//
//            execute(message);
//        }
//        catch (TelegramApiException exception){
//
//        }
//    }


    public void sendMessage(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        ReplyKeyboardRemove removeKeyboard = new ReplyKeyboardRemove();
        if (currentKeyboard == null) {
            removeKeyboard.setRemoveKeyboard(true);
            message.setReplyMarkup(removeKeyboard);
        } else {
            removeKeyboard.setRemoveKeyboard(false);
            message.setReplyMarkup(removeKeyboard);
            message.setReplyMarkup(currentKeyboard);
        }

        try {
            execute(message);
        } catch (TelegramApiException exception) {

        }
    }

}

