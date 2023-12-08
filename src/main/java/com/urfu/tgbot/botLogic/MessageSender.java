package com.urfu.tgbot.botLogic;

public interface MessageSender {
    void sendMessage(Long chatId, String message);
}
