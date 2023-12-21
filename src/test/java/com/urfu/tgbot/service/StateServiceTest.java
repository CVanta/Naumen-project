package com.urfu.tgbot.service;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.model.State;
import com.urfu.tgbot.repository.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StateServiceTest {
    private final long chatId = 1234567890;
    @InjectMocks
    private StateService stateService;
    @Mock
    private StateRepository stateRepository;

    /**
     * Тест для метода updateState, который проверяет обновление состояния, когда получает новое состояние.
     */
    @Test
    void testUpdateStateNewState() {
        StateEnum newState = StateEnum.WAITING_FOR_INPUT_NAME;
        State newStateUser = new State(chatId, newState);
        when(stateRepository.save(newStateUser)).thenReturn(newStateUser);
        stateService.updateState(chatId, newState);
        verify(stateRepository).save(new State(chatId, newState));
    }


    /**
     * Тест для метода getState, который проверяет получение текущего состояния, когда состояние существует.
     */
    @Test
    void testGetState_ExistingState() {
        StateEnum expectedState = StateEnum.WAITING_FOR_INPUT_NAME;
        State existingState = new State(chatId, expectedState);
        when(stateRepository.findById(chatId)).thenReturn(Optional.of(existingState));
        StateEnum currentState = stateService.getState(chatId);
        assertEquals(expectedState, currentState);
    }


    /**
     * Тест для метода getState, который проверяет получение текущего состояния, когда указанного chatId
     * нет в репозитории, ожидается получение null.
     */
    @Test
    void testGetState_NonExistingState() {
        when(stateRepository.findById(chatId)).thenReturn(Optional.empty());
        StateEnum currentState = stateService.getState(chatId);
        assertNull(currentState);
    }
}