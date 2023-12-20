//package com.urfu.tgbot.command;
//
//import com.urfu.tgbot.enumeration.StateEnum;
//import com.urfu.tgbot.model.Trip;
//import com.urfu.tgbot.model.User;
//import com.urfu.tgbot.service.StateService;
//import com.urfu.tgbot.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.verify;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ProfileCommandTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private StateService stateService;
//
//    @InjectMocks
//    private ProfileCommand profileCommand;
//
//    @Test
//    public void testViewTripsWhenNoTrips() {
//        long chatID = 12345L;
//        User user = new User();
//        when(userService.getUserByChatID(chatID)).thenReturn(user);
//
//        String expectedText = "Вы не записаны ни на одну поездку";
//        String result = profileCommand.viewTrips(chatID);
//        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
//        assertEquals(expectedText, result);
//    }
//
//    @Test
//    public void testViewTripsWithTrips() {
//        long chatID = 12345L;
//        User user = new User(chatID);
//        Trip trip1 = new Trip.TripBuilder().driverID(chatID).destination("Радиофак").timeTrip("22-01-24 15:15").build();
//        Trip trip2 =  new Trip.TripBuilder().driverID(chatID).destination("Уги").timeTrip("22-02-25 16:15").build();
//        user.addTrip(trip1);
//        user.addTrip(trip2);
//        when(userService.getUserByChatID(chatID)).thenReturn(user);
//
//        String expectedText = "Поездки, на которые вы записались \n1. Радиофак22-01-24 15:15\n2. Уги22-02-25 16:15\n0 - для выхода в режим комманд";
//        String result = profileCommand.viewTrips(chatID);
//        assertEquals(expectedText, result);
//    }
//
//    @Test
//    public void testChangeState() {
//        Long chatID = 12345L;
//        profileCommand.changeState(chatID);
//        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_INPUT_DEL);
//    }
//}