package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListCommand {

    private final TripService tripService;
    private final StateService stateService;


    @Autowired
    public ListCommand(TripService tripService, StateService stateService) {
        this.tripService = tripService;
        this.stateService = stateService;
    }

    public String getAllAvailableTrips() {
        List<Trip> trips = tripService.getTripsWithPassengersMoreThanZero();

        String result = "";
        int num = 1;
        for (Trip trip : trips) {
            String t_string = trip.getTimeTrip() + " " +
                    trip.getDestination() + " мест:" +
                    trip.getFreePlaces();
            result += num + ". " + t_string + "\n";
            num += 1;
        }
        return result;
    }

    public void changeState(long chatID) {
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_TRIP_NUMBER);
    }

}
