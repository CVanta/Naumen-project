package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListCommandTest {

    @Mock
    private TripService tripService;

    @Mock
    private StateService stateService;

    @InjectMocks
    private ListCommand listCommand;

    @Test
    public void testGetAllAvailableTrips() {
        List<Trip> fakeTrips = new ArrayList<>();
        Trip trip1 = new Trip.TripBuilder().destination("Абоба").timeTrip("22-01-24 15:15").freePlaces(5).build();
        Trip trip2 = new Trip.TripBuilder().destination("Радиофак").timeTrip("21-01-26 23:15").freePlaces(3).build();
        fakeTrips.add(trip1);
        fakeTrips.add(trip2);
        when(tripService.getAvailableTrips()).thenReturn(fakeTrips);
        String expectedText = "1. 22-01-24 15:15 Абоба мест:5\n2. 21-01-26 23:15 Радиофак мест:3\n0 - для выхода в режим комманд";
        String result = listCommand.getAllAvailableTrips();
        assertEquals(expectedText, result);
    }

    @Test
    public void testChangeState() {
        long chatID = 123456L;
        listCommand.changeState(chatID);
        verify(stateService).updateState(chatID, States.WAITING_FOR_INPUT_TRIP_NUMBER);
    }
}