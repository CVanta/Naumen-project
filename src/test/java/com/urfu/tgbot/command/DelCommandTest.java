package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.TripService;
import com.urfu.tgbot.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DelCommandTest {

    private final long chatID = 12345L;
    private final User user = new User();
    private final Trip trip = new Trip();
    @InjectMocks
    private DelCommand delCommand;
    @Mock
    private TripService tripService;
    @Mock
    private UserService userService;

    @Test
    public void testDelTrip_SuccessfullyCanceled() {
        user.addTrip(trip);
        when(userService.getUserByChatID(chatID)).thenReturn(user);
        String result = delCommand.delTrip(chatID, 1);
        assertEquals("Ваша запись на поездку успешно удалена.", result);
    }

    @Test
    public void testDelTrip_InvalidTripNumber() {
        user.addTrip(trip);
        when(userService.getUserByChatID(chatID)).thenReturn(user);
        String result = delCommand.delTrip(chatID, 2);
        assertEquals("Не сущетсвует поездки с таким номером, выберите поездку из списка.", result);
    }
}
