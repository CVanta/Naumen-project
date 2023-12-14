package com.urfu.tgbot.services;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.State;
import com.urfu.tgbot.repositories.StatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс сервиса для управления состояниями Telegram-бота.
 */
@Component
@Service
public class StateService {
    private final StatesRepository statesRepository;

    @Autowired
    public StateService(StatesRepository statesRepository) {
        this.statesRepository = statesRepository;
    }

    /**
     * Сохраняет новое состояние в базу данных.
     *
     * @param chatID Идентификатор чата.
     * @param state Состояние для сохранения.
     */
    public State saveState(Long chatID, States state) {
        State newState = new State(chatID, state);
        statesRepository.save(newState);
        return newState;
    }

    /**
     * Обновляет существующее состояние в базе данных.
     *
     * @param chatID Идентификатор чата.
     * @param state Новое состояние.
     */
    public State updateState(Long chatID, States state) {
        State newState = new State(chatID, state);
        statesRepository.deleteById(chatID);
        statesRepository.save(newState);
        return newState;
    }

    /**
     * Возвраащает текущее состояние бота.
     * @param chatID Идентификатор чата.
     * @return Текущее состояние бота.
     */
    public States getState(Long chatID) {
        if (statesRepository.findById(chatID).isPresent())
            return statesRepository.findById(chatID).get().getState();
        return null;
    }
}
