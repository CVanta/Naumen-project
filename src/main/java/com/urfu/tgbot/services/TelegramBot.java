package com.urfu.tgbot.services;

import com.urfu.tgbot.commands.CommandManager;
import com.urfu.tgbot.configs.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final CommandManager commandManager;

    public TelegramBot(BotConfig config, CommandManager commandManager) {
        this.config = config;
        this.commandManager = commandManager;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            String answer = commandManager.readInput(messageText, chatID);
            sendMessage(chatID, answer);
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {

            execute(message);
        } catch (TelegramApiException exception) {

        }
    }
}