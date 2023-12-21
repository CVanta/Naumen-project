package com.urfu.tgbot.telegramBot;

import com.urfu.tgbot.command.CommandManager;
import com.urfu.tgbot.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Класс для получения сообщений от пользователя и отправки ответа, а так же для инициализации самого ТГ-бота.
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final CommandManager commandManager;

    @Autowired
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
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            String answer = commandManager.handleInputUpdateState(messageText, chatID);
            try {
                sendMessage(chatID, answer);
            } catch (TelegramApiException exception) {
                System.err.println(exception);
            }
        }
    }

    /**
     * Отправляет сообщение в указанный идентификатор чата.
     *
     * @param chatId     идентификатор чата.
     * @param textToSend Сообщение, которое нужно отправить.
     */
    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {

            execute(message);
        } catch (TelegramApiException exception) {
            System.err.println(exception);
        }
    }

    /**
     * Инициализирует Telegram-бота, регистрируя его с помощью Telegram API.
     * @throws TelegramApiException Исключение Telegram Api, если при регистрации бота возникает ошибка
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        try {
            api.registerBot(this);
        } catch (TelegramApiException exception) {
            System.err.println("Не удалось инициализировать бота");
        }
    }
}
