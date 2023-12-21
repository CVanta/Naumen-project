package com.urfu.tgbot.service;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.model.State;
import com.urfu.tgbot.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Класс сервиса для управления состояниями Telegram-бота.
 */
@Service
public class StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    /**
     * Обновляет состояние в базе данных.
     *
     * @param chatID Идентификатор чата.
     * @param state Новое состояние.
     */
    public void updateState(Long chatID, StateEnum state) {
        State newState = new State(chatID, state);
        stateRepository.save(newState);
    }

    /**
     * Возвращает текущее состояние бота.
     *
     * @param chatID Идентификатор чата.
     * @return Текущее состояние бота.
     */
    public StateEnum getState(Long chatID) {
        Optional<State> state = stateRepository.findById(chatID);
        return state.map(State::getState).orElse(null);
    }
}
