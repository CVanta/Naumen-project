package com.urfu.tgbot.commands;

import com.urfu.tgbot.commands.ShowCommand;
import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShowCommandTest {

    @Mock
    private StateService stateService;

    @Mock
    private TripService tripService;

    @InjectMocks
    private ShowCommand showCommand;

    @Test
    public void testChangeState() {
        long chatID = 12345L;
        showCommand.changeState(chatID);
        verify(stateService).updateState(chatID, States.WAITING_FOR_COMMAND);
    }

    @Test
    public void testGetBotTextWithPassengers() {
        long chatID = 12345L;
        int number = 1;
        Trip trip = new Trip();
        trip.getPassengers().add(new User.Builder(111).username("Петров Иван Иваныч").tgUsername("tgUsername1").phoneNumber(79123456789L).build());
        trip.getPassengers().add(new User.Builder(222).username("Чапаев Василий Иванович").tgUsername("tgUsername2").phoneNumber(79123456789L).build());
        when(tripService.getAllTripsByChatId(anyLong())).thenReturn(Collections.singletonList(trip));
        String expectedText = "1. @tgUsername1 Петров Иван Иваныч 79123456789\n2. @tgUsername2 Чапаев Василий Иванович 79123456789\n";
        String botText = showCommand.getBotText(chatID, number);
        assertEquals(expectedText, botText);
    }

    @Test
    public void testGetBotTextWithoutPassengers(){
        long chatID = 12345L;
        int number = 1;
        Trip trip = new Trip();
        when(tripService.getAllTripsByChatId(anyLong())).thenReturn(Collections.singletonList(trip));
        String expectedText = "К вам никто не записался на поездку.";
        String botText = showCommand.getBotText(chatID, number);
        assertEquals(expectedText,  botText);

    }
}