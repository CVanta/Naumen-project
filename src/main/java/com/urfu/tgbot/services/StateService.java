package com.urfu.tgbot.services;

import com.urfu.tgbot.models.State;
import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.repositories.StatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class StateService {
    private List<State> states;

    private final StatesRepository statesRepository;

    @Autowired
    public StateService(StatesRepository statesRepository) {
        this.statesRepository = statesRepository;
    }


    public List<State> getAllStates() {
        List<State> allStates = new ArrayList<>();
        statesRepository.findAll().forEach(allStates::add);
        return states;
    }


    public void saveState(Long chatID, States state) {
        State newState = new State(chatID, state);
        statesRepository.save(newState);
    }

    public void updateState(Long chatID, States state) {
        State newState = new State(chatID, state);
        statesRepository.deleteById(chatID);
        statesRepository.save(newState);
    }

    public States getState(Long chatID) {
        if (statesRepository.findById(chatID).isPresent())
            return statesRepository.findById(chatID).get().getState();
        return null;
    }


}
