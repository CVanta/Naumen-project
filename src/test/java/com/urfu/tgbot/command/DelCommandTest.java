//package com.urfu.tgbot.command;
//
//import com.urfu.tgbot.enumeration.StateEnum;
//import com.urfu.tgbot.model.Trip;
//import com.urfu.tgbot.model.User;
//import com.urfu.tgbot.service.StateService;
//import com.urfu.tgbot.service.TripService;
//import com.urfu.tgbot.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//    public class DelCommandTest {
//
//        @Mock
//        private StateService stateService;
//
//        @Mock
//        private UserService userService;
//
//        @Mock
//        private TripService tripService;
//
//        @InjectMocks
//        private DelCommand delCommand;
//
//        @Test
//        public void testChangeState() {
//            long chatID = 12345L;
//            delCommand.changeState(chatID);
//            verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
//        }
//
//        @Test
//        public void testDelTrip() throws Exception {
//            long chatID = 12345L;
//            int tripNumber = 1;
//            User user = new User("Василий Иванович Чапаев", 111, "aboba");
//            Trip trip = new Trip.TripBuilder().driverID(chatID).destination("Радиофак").timeTrip("22-01-24 15:15").build();
//            user.addTrip(trip);
//            when(userService.getUserByChatID(anyLong())).thenReturn(user);
//            String result = delCommand.delTrip(chatID, tripNumber);
//            verify(tripService).addTrip(trip);
//            verify(userService).deleteUser(user);
//            verify(userService).addUser(user);
//            assertEquals("Ваша запись на поездку успешно удалена.", result);
//        }
//    }
//
