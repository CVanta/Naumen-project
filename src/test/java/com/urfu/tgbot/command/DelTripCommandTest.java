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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DelTripCommandTest {
    @Mock
    private TripService tripService;
    @Mock
    private UserService userService;
    @InjectMocks
    private DelTripCommand delTripCommand;

    /**
     * Проверяет успешное удаление поездки..
     */
    @Test
    public void testDelTrip_SuccessfullyDeleted() {
        Trip trip = new Trip();
        User passenger = new User();
        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip);
        passenger.addTrip(trip);
        trip.addPassenger(passenger);
        when(tripService.getAllTripsByChatId(anyLong())).thenReturn(tripList);
        String result = delTripCommand.delTrip(12345L, 1);
        assertEquals("Поездка успешно удалена.", result);
    }
}