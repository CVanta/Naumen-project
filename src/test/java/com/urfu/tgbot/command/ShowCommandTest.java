package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ShowCommandTest {


    private final long chatId = 12345L;
    private final int number = 1;
    @Mock
    private TripService tripService;
    @InjectMocks
    private ShowCommand showCommand;

    /**
     * Проверяет, что метод getBotText возвращает корректный текст с информацией о пассажирах для случая,
     * когда на выбранную поездку есть записавшиеся пассажиры.
     */

    @Test
    public void testGetBotTextWithPassengers() {
        Trip trip = new Trip();
        trip.getPassengers().add(new User("Петров Иван Иваныч", "tgUsername1", 111,
                "79123456789"));
        trip.getPassengers().add(new User("Чапаев Василий Иванович", "tgUsername2", 222,
                "79987654321"));
        when(tripService.getAllTripsByChatId(anyLong())).thenReturn(Collections.singletonList(trip));
        String expectedText = """
                1. @tgUsername1 Петров Иван Иваныч 79123456789
                2. @tgUsername2 Чапаев Василий Иванович 79987654321
                """;
        String botText = showCommand.getBotText(chatId, number);
        assertEquals(expectedText, botText);
    }

    /**
     * Проверяет, что метод getBotText корректно отрабатывает для случая,
     * когда на выбранную поездку нет записавшихся пассажиров.
     */

    @Test
    public void testGetBotTextWithoutPassengers() {
        Trip trip = new Trip();
        when(tripService.getAllTripsByChatId(anyLong())).thenReturn(Collections.singletonList(trip));
        String expectedText = "К вам никто не записался на поездку.";
        String botText = showCommand.getBotText(chatId, number);
        assertEquals(expectedText, botText);
    }
}