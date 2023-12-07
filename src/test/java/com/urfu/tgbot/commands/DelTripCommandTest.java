package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DelTripCommandTest {

    @Mock
    private StateService stateService;

    @Mock
    private TripService tripService;

    @InjectMocks
    private DelTripCommand delTripCommand;

    @Test
    public void testChangeState() {
        long chatID = 12345L;
        delTripCommand.changeState(chatID);
        verify(stateService).updateState(chatID, States.WAITING_FOR_COMMAND);
    }

    @Test
    public void testDelTrip() {
        long chatID = 12345L;
        int tripNumber = 1;
        User user = new User(chatID);
        user.addTrip(new Trip.TripBuilder().driverID(chatID).destination("Радиофак").timeTrip("22-01-24 15:15").build());
        List<Trip> tripList = user.getTripList();
        when(tripService.getAllTripsByChatId(anyLong())).thenReturn(tripList);
        String result = delTripCommand.delTrip(chatID, tripNumber);
        verify(tripService).deleteTrip(any(Trip.class));
        assertEquals("Поездка успешно удалена.", result);
    }
}