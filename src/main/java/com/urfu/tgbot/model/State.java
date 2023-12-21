package com.urfu.tgbot.model;

import com.urfu.tgbot.enumeration.StateEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        State state = (State) o;

        return Objects.equals(chatId, state.chatId);
    }

    @Override
    public int hashCode() {
        return chatId != null ? chatId.hashCode() : 0;
    }
}
