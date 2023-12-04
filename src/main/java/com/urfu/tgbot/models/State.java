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

    /**
     * Возвращает текущее состояние чата.
     *
     * @return Текущее состояние чата.
     */
    public States getState() {
        return state;
    }
}
