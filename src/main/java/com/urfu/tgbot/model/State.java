package com.urfu.tgbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Класс сущности, представляющий состояние чата.
 */
@Entity
@Table(name = "states")
public class State {
    @Id
    private Long chatId;

    private com.urfu.tgbot.enums.State state;

    public State() {

    }

    public State(Long chatId, com.urfu.tgbot.enums.State state) {
        this.chatId = chatId;
        this.state = state;
    }

    /**
     * Возвращает текущее состояние чата.
     *
     * @return Текущее состояние чата.
     */
    public com.urfu.tgbot.enums.State getState() {
        return state;
    }
}
