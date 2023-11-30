package com.urfu.tgbot.services;

import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.urfu.tgbot.repositories.TripRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        tripRepository.findAll().forEach(trips::add);
        return trips;
    }

    public List<Trip> getTripsWithPassengersMoreThanZero() {
        Iterable<Trip> iterable = () -> tripRepository.findAll().iterator();
        Stream<Trip> stream = StreamSupport.stream(iterable.spliterator(), false);
        return stream.filter(x -> x.getFreePlaces() > 0).toList();
    }


    public Trip saveTrip(long driverID, String listPassenger, String destination, String timeTrip, int freePlaces) {
        Trip trip = new Trip(driverID, listPassenger, destination, timeTrip, freePlaces);
        tripRepository.save(trip);
        return trip;
    }

    public void addTrip(Trip trip) {
        tripRepository.save(trip);
    }

    public void deleteTrip(Trip trip) {
        if (tripRepository.findById(trip.getId()).isEmpty()) {
            throw new IllegalArgumentException("trip not exist");
        }
        tripRepository.delete(trip);
    }


    public List<Trip> getAllTripsByChatId(long chatID){
        Iterable<Trip> iterable = () -> tripRepository.findAll().iterator();
        Stream<Trip> stream = StreamSupport.stream(iterable.spliterator(), false);
        return stream.filter(x -> x.getDriverID() == chatID).max(Comparator.comparing(Trip::getId)).stream().toList();
    }

    public Trip getLastTripChatID(long chatID) {
        Iterable<Trip> iterable = () -> tripRepository.findAll().iterator();
        Stream<Trip> stream = StreamSupport.stream(iterable.spliterator(), false);
        return stream.filter(x -> x.getDriverID() == chatID).max(Comparator.comparing(x -> x.getId())).get();
    }

    public void addUserToTrip(Trip trip, User user) {
        trip.addPassenger(user);
        trip.decrementFreePlaces();
        user.addTrip(trip);
        tripRepository.save(trip);
    }
}
