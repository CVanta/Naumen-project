package com.urfu.tgbot.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Класс для настройки Telegram-бота.
 */
@Configuration
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
     * Задает имя Telegram-бота.
     *
     * @param botName Имя телеграм-бота.
     */

    public void setBotName(String botName) {
        this.botName = botName;
    }

    /**
     * Возвращает токен Telegram-бота.
     *
     * @return Токен телеграм-бота.
     */
    public String getBotToken() {
        return botToken;
    }

    /**
     * Задает токен Telegram-бота.
     *
     * @param botToken Токен телеграм-бота
     */
    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
