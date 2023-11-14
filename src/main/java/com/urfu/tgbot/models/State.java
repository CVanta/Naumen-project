package com.urfu.tgbot.models;

import com.urfu.tgbot.enums.States;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "states")
public class State {
    @Id
    private Long chatId;

    private States state;

    public State() {

    }

    public State(Long chatId, States state) {
        this.chatId = chatId;
        this.state = state;
    }

    public Long getChatId() {
        return chatId;
    }

    private void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public States getState() {
        return state;
    }

    private void setState(States state) {
        this.state = state;
    }

}
