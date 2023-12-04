package com.urfu.tgbot.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("application.properties")
public class BotConfig {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    /**
     * Возвращает имя бота.
     *
     * @return Имя бота.
     */
    public String getBotName() {
        return botName;
    }

    /**
     * Возвращает токенбота.
     *
     * @return Токен бота.
     */
    public String getBotToken() {
        return botToken;
    }
}
