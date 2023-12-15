package com.urfu.tgbot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Класс для настройки Telegram-бота.
 */
@Component
@PropertySource("application.properties")
public class BotConfig {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    /**
     * Возвращает имя Telegram-бота.
     *
     * @return Имя телеграм-бота.
     */
    public String getBotName() {
        return botName;
    }


    /**
     * Возвращает токен Telegram-бота.
     *
     * @return Токен телеграм-бота.
     */
    public String getBotToken() {
        return botToken;
    }
}
