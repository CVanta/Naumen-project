package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {

    @Mock
    private TripService tripService;

    @InjectMocks
    private ListCommand listCommand;

    /**
     * Проверяет, что метод getAllAvailableTrips возвращает корректный текст со списком доступных поездок.
     */

    @Test
    public void testListCommand() {
        List<Trip> fakeTrips = new ArrayList<>();
        fakeTrips.add(new Trip(111, "Абоба", "22-01-24 15:15", 5));
        fakeTrips.add(new Trip(111, "Радиофак", "21-01-26 23:15", 3));
        when(tripService.getAvailableTrips()).thenReturn(fakeTrips);
        String expectedText = """
                1. 22-01-24 15:15 Абоба мест:5
                2. 21-01-26 23:15 Радиофак мест:3
                0 - для выхода в режим комманд""";
        String result = listCommand.getAllAvailableTrips();
        assertEquals(expectedText, result);
    }
}