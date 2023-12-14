package com.urfu.tgbot.configs;

import com.urfu.tgbot.services.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Класс для инициализации Telegram-бота.
 */
@Component
public class BotInitializer {
    @Autowired
    private TelegramBot bot;

    /**
     * Инициализирует Telegram-бота, регистрируя его с помощью Telegram API.
     * @throws TelegramApiException Исключение Telegram Api, если при регистрации бота возникает ошибка
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        try {
            api.registerBot(bot);
        } catch (TelegramApiException exception) {

        }
    }
}
