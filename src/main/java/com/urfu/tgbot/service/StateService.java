package com.urfu.tgbot.service;

import com.urfu.tgbot.enums.State;
import com.urfu.tgbot.repository.StatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    public com.urfu.tgbot.model.State saveState(Long chatID, State state) {
        com.urfu.tgbot.model.State newState = new com.urfu.tgbot.model.State(chatID, state);
        statesRepository.save(newState);
        return newState;
    }

    /**
     * Обновляет существующее состояние в базе данных.
     *
     * @param chatID Идентификатор чата.
     * @param state Новое состояние.
     */
    public com.urfu.tgbot.model.State updateState(Long chatID, State state) {
        com.urfu.tgbot.model.State newState = new com.urfu.tgbot.model.State(chatID, state);
        statesRepository.deleteById(chatID);
        statesRepository.save(newState);
        return newState;
    }

    /**
     * Возвраащает текущее состояние бота.
     * @param chatID Идентификатор чата.
     * @return Текущее состояние бота.
     */
    public State getState(Long chatID) {
        if (statesRepository.findById(chatID).isPresent())
            return statesRepository.findById(chatID).get().getState();
        return null;
    }
}
