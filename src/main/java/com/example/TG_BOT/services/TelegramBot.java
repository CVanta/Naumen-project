package com.example.TG_BOT.services;

import com.example.TG_BOT.configs.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
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
        if (update.hasMessage() && update.getMessage().hasText())
        {
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    try {
                        startCommandReceived(chatID);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }

        }

    }

    private void startCommandReceived(Long chatID) throws TelegramApiException {
        String answer = "ABOBA";
        sendMessage(chatID, answer);
    }

    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        execute(message);
    }
}
