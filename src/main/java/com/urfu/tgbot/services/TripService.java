package com.urfu.tgbot.services;

import com.urfu.tgbot.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.urfu.tgbot.repositories.TripRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class TripService {

    private List<Trip> trips;
    private static TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        TripService.tripRepository = tripRepository;
    }

    public static List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        tripRepository.findAll().forEach(trips::add);
        return trips;
    }


    public Trip saveTrip(String driver, String listPassenger, String destination, String timeTrip) {
        Trip trip = new Trip(driver, listPassenger, destination, timeTrip);
        tripRepository.save(trip);
        return trip;
    }
}
