package com.urfu.tgbot.service;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    private final Trip trip = new Trip(111, "Уги", "31-12-23 23:59", 2);
    private final Trip trip1 = new Trip(111, "Радиофак", "01-01-24 05:00", 1);
    @InjectMocks
    private TripService tripService;
    @Mock
    private TripRepository tripRepository;

    /**
     * Тестирует метод getAvailableTrips(), чтобы убедиться, что возвращается правильное количество доступных поездок.
     */
    @Test
    public void testGetAvailableTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(trip);
        trips.add(trip1);
        when(tripRepository.findAll()).thenReturn(trips);
        List<Trip> availableTrips = tripService.getAvailableTrips();
        assertEquals(2, availableTrips.size());
        assertEquals("Уги" + "31-12-23 23:59", availableTrips.get(0).getFormattedString());
    }

    /**
     * Тестирует метод addTrip(), чтобы убедиться, что поездка добавляется в репозиторий.
     */

    @Test
    public void testAddTrip() {
        tripService.addTrip(trip);
        verify(tripRepository).save(trip);
    }

    /**
     * Тестирует метод deleteTrip(), чтобы убедиться, что поездка удаляется из репозитория,
     * если она существует, и генерируется исключение, если ее нет.
     */
    @Test
    public void testDeleteTrip_Exist() {
        when(tripRepository.findById(trip.getId())).thenReturn(java.util.Optional.of(trip));
        tripService.deleteTrip(trip);
        verify(tripRepository).delete(trip);
    }

    /**
     * Тестирует метод deleteTrip(), чтобы убедиться, что поездка удаляется из репозитория,
     * если она существует, и генерируется исключение, если ее нет.
     */
    @Test
    public void testDeleteTrip_NotExist() {
        when(tripRepository.findById(trip.getId())).thenReturn(java.util.Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tripService.deleteTrip(trip);
        });
        assertEquals("trip not exist", exception.getMessage());
    }

    /**
     * Тестирует метод deleteTrip(), чтобы убедиться, что генерируется исключение, если поездка не существует.
     */

    @Test
    public void testGetAllTripsByChatId() {
        List<Trip> trips = new ArrayList<>();
        trips.add(trip);
        trips.add(trip1);
        when(tripRepository.findAll()).thenReturn(trips);
        List<Trip> tripsByChatId = tripService.getAllTripsByChatId(111);
        assertEquals(2, tripsByChatId.size());
    }

    /**
     * Тестирует метод getLastTripChatID(), чтобы убедиться, что возвращается последняя поездка для указанного chatID.
     */

    @Test
    public void testGetLastTripChatID() throws Exception {
        Trip mockTrip = mock(Trip.class);
        Trip mockTrip1 = mock(Trip.class);
        long chatID = 12345L;
        when(mockTrip.getDriverID()).thenReturn(chatID);
        when(mockTrip1.getDriverID()).thenReturn(chatID);
        when(mockTrip.getId()).thenReturn(1L);
        when(mockTrip1.getId()).thenReturn(2L);
        List<Trip> trips = new ArrayList<>();
        trips.add(mockTrip);
        trips.add(mockTrip1);
        when(tripRepository.findAll()).thenReturn(trips);
        assertEquals(mockTrip1, tripService.getLastTripChatID(chatID));
    }

    /**
     * Тестирует метод addUserToTrip(), чтобы убедиться, что пользователь успешно добавляется к поездке.
     */

    @Test
    public void testAddUserToTrip() {
        User user = new User(1111);
        tripService.addUserToTrip(trip1, user);
        assertEquals(1, trip1.getPassengers().size());
        assertEquals(0, trip1.getFreePlaces());
        assertEquals(user.getTripList().get(0), trip1);
    }
}