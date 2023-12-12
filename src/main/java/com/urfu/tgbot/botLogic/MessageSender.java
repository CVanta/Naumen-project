package com.urfu.tgbot.botLogic;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface MessageSender {
    void sendMessage(Long chatId, String message);
}
