package com.example.TG_BOT.services;

import com.example.TG_BOT.models.Driver;
import com.example.TG_BOT.models.Passenger;
import com.example.TG_BOT.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repositories.TripRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Service
@EnableAutoConfiguration
public class TripService {

    private List<Trip> trips;
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;

    }


    public TripService(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        tripRepository.findAll().forEach(trips::add);
        return trips;
    }


    public Trip saveTrip(String driver, String listPassenger, String destination, Date timeTrip) {
        Trip trip = new Trip(driver, listPassenger, destination, timeTrip);
        tripRepository.save(trip);
        return trip;
    }

}
