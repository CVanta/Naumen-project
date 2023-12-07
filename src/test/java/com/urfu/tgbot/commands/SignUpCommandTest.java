package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import com.urfu.tgbot.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpCommandTest {

    @Mock
    private StateService stateService;

    @Mock
    private TripService tripService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SignUpCommand signUpCommand;
    @InjectMocks
    private AddingTrip addingTrip;

    private User user;
    private Trip trip;
    @Mock
    private User mockUser;

    @Before
    public void setup() {
        long chatId = 1111;
        user = new User(chatId);
        trip = new Trip.TripBuilder().driverID(chatId).destination("Уги").timeTrip("22-01-24 15:15").build();
        when(userService.getUserByChatID(anyLong())).thenReturn(user);
        when(tripService.getAvailableTrips()).thenReturn(Collections.singletonList(trip));
    }

    @Test
    public void testChangeState() {
        long chatID = 12345;
        signUpCommand.changeState(chatID);
        verify(stateService).updateState(chatID, States.WAITING_FOR_COMMAND);
    }

    @Test
    public void testRegisterUser_tripNotInList() {
        int numberTrip = 2;
        long chatID = 12345;
        assertEquals("Номера поездки нет в списке", signUpCommand.registerUser(numberTrip, chatID));
    }

    @Test
    public void testRegisterUser_userIsDriver() {
        int numberTrip = 1;
        long chatID = 1111;
        assertEquals("Вы не можете записаться на свою поездку!", signUpCommand.registerUser(numberTrip, chatID));
    }

    @Test
    public void testRegisterUser_success() {
        String destination = "Уги";
        String time = "22-01-24 15:15";
        user.addTrip(new Trip(1, "Ааа"));
        when(userService.getUserByChatID(anyLong())).thenReturn(user);
        when(tripService.getAvailableTrips()).thenReturn(Collections.singletonList(trip));
        assertEquals("Вы записались на поездку: " + destination + time, signUpCommand.registerUser(1, 12345L));
        verify(tripService).addUserToTrip(trip, user);
        verify(userService).addTripToUser(trip, user);
        verify(stateService).updateState(12345L, States.WAITING_FOR_COMMAND);
    }
}
