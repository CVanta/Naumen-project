package com.urfu.tgbot.model;

import com.urfu.tgbot.enums.StateEnum;
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

    private StateEnum state;

    public State() {

    }

    public State(Long chatId, StateEnum state) {
        this.chatId = chatId;
        this.state = state;
    }

    /**
     * Возвращает текущее состояние чата.
     *
     * @return Текущее состояние чата.
     */
    public StateEnum getState() {
        return state;
    }
}
