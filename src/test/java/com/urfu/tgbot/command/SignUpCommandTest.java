package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.TripService;
import com.urfu.tgbot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignUpCommandTest {

    private final long chatId = 1111;
    private final User user = new User(chatId);
    private final Trip trip = new Trip(chatId, "Уги", "22-01-24 15:15");
    @Mock
    private StateService stateService;
    @Mock
    private TripService tripService;
    @Mock
    private UserService userService;
    @InjectMocks
    private SignUpCommand signUpCommand;

    @BeforeEach
    void setUp() {
        when(tripService.getAvailableTrips()).thenReturn(Collections.singletonList(trip));
    }

    /**
     * Проверяет, что метод registerUser корректно обрабатывает случай
     * когда номер поездки не найден в списке доступных поездок.
     */

    @Test
    public void testRegisterUser_tripNotInList() {
        assertEquals("Номера поездки нет в списке", signUpCommand.registerUser(2, chatId));
    }

    /**
     * Проверяет, что метод registerUser корректно обрабатывает случай,
     * когда пользователь является водителем выбранной поездки.
     */

    @Test
    public void testRegisterUser_userIsDriver() {
        assertEquals("Вы не можете записаться на свою поездку!",
                signUpCommand.registerUser(1, chatId));
    }

    /**
     * Проверяет, что метод registerUser корректно обрабатывает случай,
     * когда пользователь уже записан на выбранную поездку.
     */
    @Test
    public void testRegisterUserSecondTimeRegistration() {
        User newUser = new User(1234);
        newUser.addTrip(trip);
        when(userService.getUserByChatID(anyLong())).thenReturn(newUser);
        assertEquals("Вы не можете записаться на поездку дважды!",
                signUpCommand.registerUser(1, 1234));

    }

    /**
     * Проверяет успешную регистрацию пользователя на поездку и соответствующее обновление данных о поездке,
     * пользователе и состоянии.
     */

    @Test
    public void testRegisterUser_success() {
        long newChatId = 12345;
        user.addTrip(new Trip(1, "Ааа"));
        when(userService.getUserByChatID(anyLong())).thenReturn(user);
        assertEquals("Вы записались на поездку: " + "Уги" + "22-01-24 15:15",
                signUpCommand.registerUser(1, newChatId));
        verify(tripService).addUserToTrip(trip, user);
        verify(stateService).updateState(newChatId, StateEnum.WAITING_FOR_COMMAND);
    }
}
