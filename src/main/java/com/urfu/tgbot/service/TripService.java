package com.urfu.tgbot.service;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.urfu.tgbot.repository.TripRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Класс обслуживания для управления поездками
 */
@Service
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
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
    public Trip getLastTripChatID(long chatID) throws Exception {
        Iterable<Trip> iterable = () -> tripRepository.findAll().iterator();
        Stream<Trip> stream = StreamSupport.stream(iterable.spliterator(), false);
        Optional<Trip> tripOptional = stream.filter(x -> x.getDriverID() == chatID).max(Comparator.comparing(Trip::getId));
        if (tripOptional.isPresent()){
            return tripOptional.get();
        }
        throw new Exception("Trip not exist");
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
        addTrip(trip);
    }
}
