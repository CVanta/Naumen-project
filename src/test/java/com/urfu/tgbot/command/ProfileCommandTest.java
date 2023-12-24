package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileCommandTest {

    private final long chatID = 12345L;
    @Mock
    private UserService userService;
    @Mock
    private StateService stateService;
    @InjectMocks
    private ProfileCommand profileCommand;

    @Test
    public void testViewTripsWithoutTrips() {
        when(userService.getUserByChatID(chatID)).thenReturn(new User());
        String expectedText = "Вы не записаны ни на одну поездку";
        String result = profileCommand.viewTrips(chatID);
        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
        assertEquals(expectedText, result);
    }

    @Test
    public void testViewTripsWithTrips() {
        User user = new User(chatID);
        user.addTrip(new Trip(chatID, "Радиофак", "22-01-24 15:15"));
        user.addTrip(new Trip(chatID, "Уги", "22-02-25 16:15"));
        when(userService.getUserByChatID(chatID)).thenReturn(user);
        String expectedText = """
                Поездки, на которые вы записались
                1. Радиофак22-01-24 15:15
                2. Уги22-02-25 16:15
                0 - для выхода в режим комманд""";
        String result = profileCommand.viewTrips(chatID);
        assertEquals(expectedText, result);
    }

}