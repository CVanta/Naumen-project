package com.urfu.tgbot.service;

import com.urfu.tgbot.enums.StateEnum;
import com.urfu.tgbot.model.State;
import com.urfu.tgbot.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class StateServiceTest {

    private StateService stateService;
    private StateRepository stateRepository;

    @BeforeEach
    void setUp() {
        stateRepository = mock(StateRepository.class);
        stateService = new StateService(stateRepository);
    }

    /**
     * Тест для метода updateState, который проверяет обновление состояния, когда получает новое состояние.
     */
    @Test
    void testUpdateStateNewState() {
        long chatId = 1234567890;
        StateEnum newState = StateEnum.WAITING_FOR_INPUT_NAME;
        State newStateUser = new State(chatId, newState);
        when(stateRepository.findById(chatId)).thenReturn(Optional.empty());
        when(stateRepository.save(newStateUser)).thenReturn(newStateUser);
        stateService.updateState(chatId, newState);
        verify(stateRepository).findById(chatId);
        verify(stateRepository).save(new State(chatId, newState));
    }

    /**
     * Тест для метода updateState, который проверяет обновление состояния, когда состояние уже существует.
     */
    @Test
    void testUpdateState_ExistingState() {
        long chatId = 1234567890;
        StateEnum newState = StateEnum.WAITING_FOR_INPUT_INSTITUTE;
        State existingState = new State(chatId, StateEnum.WAITING_FOR_INPUT_NAME);
        when(stateRepository.findById(chatId)).thenReturn(Optional.of(existingState));
        stateService.updateState(chatId, newState);
        InOrder inOrder = inOrder(stateRepository);
        inOrder.verify(stateRepository).deleteById(chatId);
        inOrder.verify(stateRepository).save(new State(chatId, newState));
    }


    /**
     * Тест для метода getState, который проверяет получение текущего состояния, когда состояние уже существует.
     */
    @Test
    void testGetState_ExistingState() {
        long chatId = 1234567890;
        StateEnum expectedState = StateEnum.WAITING_FOR_INPUT_NAME;
        State existingState = new State(chatId, expectedState);
        when(stateRepository.findById(chatId)).thenReturn(Optional.of(existingState));
        StateEnum currentState = stateService.getState(chatId);
        assertEquals(expectedState, currentState);
    }


    /**
     * Тест для метода getState, который проверяет получение текущего состояния, когда состояния не существует.
     */
    @Test
    void testGetState_NonExistingState() {
        long chatId = 1234567890;
        when(stateRepository.findById(chatId)).thenReturn(Optional.empty());
        StateEnum currentState = stateService.getState(chatId);
        assertNull(currentState);
    }
}