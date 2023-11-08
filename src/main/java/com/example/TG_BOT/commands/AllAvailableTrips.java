package com.example.TG_BOT.commands;

import com.example.TG_BOT.models.Trip;
import com.example.TG_BOT.services.TripService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllAvailableTrips{
    public String getAllAvailableTrips(){
        List<Trip> trips = TripService.getAllTrips();

        StringBuilder result = new StringBuilder();

        for (Trip trip : trips) {
            result.append(trip.getDriver().toString()).append(" to ");
            result.append(trip.getDestination().toString()).append(" | ");
        }
        return result.toString();
    }

}
