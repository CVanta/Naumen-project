package com.example.TG_BOT.services;

import com.example.TG_BOT.models.Driver;
import com.example.TG_BOT.models.Passenger;
import com.example.TG_BOT.models.Trip;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TripService {

    private final List<Trip> trips;

    public TripService(List<Trip> trips) {
        this.trips = trips;
        init();
    }

    private void init() {
        trips.add(new Trip("", "", "Радиофак", new Date()));
    }

    public List<Trip> getAllTrips() {
        return trips;
    }

    public void saveTrip(String driver, String listPassenger, String destination, Date timeTrip) {
        Trip trip = new Trip(driver, listPassenger, destination, timeTrip);
        trips.add(trip);
    }
}
