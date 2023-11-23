package com.urfu.tgbot.commands;

import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.TripService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ListCommand {
    public String getAllAvailableTrips(){
        List<Trip> trips = TripService.getAllTrips();

        StringBuilder result = new StringBuilder();

        for (Trip trip : trips) {
            result.append(trip.getFreePlaces()).append(" with ");
            result.append(trip.getDriverID()).append(" at ");
            result.append(trip.getTimeTrip()).append(" to ");
            result.append(trip.getDestination()).append(" | ");
        }
        return result.toString();
    }

}
