package com.urfu.tgbot.service;

import com.urfu.tgbot.model.State;
import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    /**
     * Сохраняет новое состояние в базу данных.
     *
     * @param chatID Идентификатор чата.
     * @param state Состояние для сохранения.
     */
    public void saveState(Long chatID, StateEnum state) {
        State newState = new State(chatID, state);
        stateRepository.save(newState);
    }

    /**
     * Обновляет состояние в базе данных.
     *
     * @param chatID Идентификатор чата.
     * @param state Новое состояние.
     */
    public void updateState(Long chatID, StateEnum state) {
        State newState = new State(chatID, state);
        if(stateRepository.findById(chatID).isPresent()){
            stateRepository.deleteById(chatID);
        }
        stateRepository.save(newState);
    }

    /**
     * Возвраащает текущее состояние бота.
     * @param chatID Идентификатор чата.
     * @return Текущее состояние бота.
     */
    public StateEnum getState(Long chatID) {
        if (stateRepository.findById(chatID).isPresent())
            return stateRepository.findById(chatID).get().getState();
        return null;
    }


}
