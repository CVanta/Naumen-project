package com.urfu.tgbot.services;

import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.urfu.tgbot.repositories.TripRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
@Service
public class TripService {

    private List<Trip> trips;
    private static TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        TripService.tripRepository = tripRepository;
    }

    /**
     * Получает список всех поездок из базы данных.
     *
     * @return Список всех поездок.
     */
    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        tripRepository.findAll().forEach(trips::add);
        return trips;
    }

    /**
     * Возвращает список поездок, до начала которых больше часа и больше 0 свободных мест.
     *
     * @return Список поездок, до начала которых больше часа и больше 0 свободных мест.
     */
    public List<Trip> getAvailableTrips() {
        Iterable<Trip> iterable = () -> tripRepository.findAll().iterator();
        Stream<Trip> stream = StreamSupport.stream(iterable.spliterator(), false);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date currentDate = new Date();

        Date oneHourAhead = new Date(currentDate.getTime() + TimeUnit.HOURS.toMillis(1));

        return stream
                .filter(x -> x.getFreePlaces() > 0)
                .filter(x -> {
                    try {
                        Date tripDate = dateFormat.parse(x.getTimeTrip());
                        return tripDate.after(oneHourAhead);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
    }

    /**
     * Добавляет поездку в базу данных.
     *
     * @param driverID, listPassenger, destination Поездка для добавления.
     */
    public Trip saveTrip(long driverID, String listPassenger, String destination, String timeTrip, int freePlaces) {
        Trip trip = new Trip(driverID, listPassenger, destination, timeTrip, freePlaces);
        tripRepository.save(trip);
        return trip;
    }

    /**
     * Добавляет поездку в базу данных.
     *
     * @param trip Поездка для добавления.
     */
    public void addTrip(Trip trip) {
        tripRepository.save(trip);
    }

    /**
     * Удаляет поездку из базы данных.
     *
     * @param trip Поездка для удаления.
     * @throws IllegalArgumentException Если поездка не существует.
     */
    public void deleteTrip(Trip trip) {
        if (tripRepository.findById(trip.getId()).isEmpty()) {
            throw new IllegalArgumentException("trip not exist");
        }
        tripRepository.delete(trip);
    }


    /**
     * Получает все поездки для конкретного идентификатора чата.
     *
     * @param chatID Идентификатор чата.
     * @return Список поездок по заданному chatID.
     */
    public List<Trip> getAllTripsByChatId(long chatID){
        Iterable<Trip> iterable = () -> tripRepository.findAll().iterator();
        Stream<Trip> stream = StreamSupport.stream(iterable.spliterator(), false);
        return stream.filter(x -> x.getDriverID() == chatID).toList();
    }

    /**
     * Получает последнюю поездку для конкретного идентификатора чата.
     *
     * @param chatID Идентификатор чата.
     * @return Поездка по заданному chatID.
     */
    public Trip getLastTripChatID(long chatID) {
        Iterable<Trip> iterable = () -> tripRepository.findAll().iterator();
        Stream<Trip> stream = StreamSupport.stream(iterable.spliterator(), false);
        return stream.filter(x -> x.getDriverID() == chatID).max(Comparator.comparing(x -> x.getId())).get();
    }

    /**
     * Добавляет пользователя в поездку.
     *
     * @param trip Поездка.
     * @param user Пользователь.
     */
    public void addUserToTrip(Trip trip, User user) {
        trip.addPassenger(user);
        trip.decrementFreePlaces();
        user.addTrip(trip);
        tripRepository.save(trip);
    }
}
