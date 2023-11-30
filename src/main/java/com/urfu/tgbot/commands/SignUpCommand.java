package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignUpCommand {
    private final StateService stateService;
    private final TripService tripService;

    private final UserService userService;

    @Autowired
    public SignUpCommand(StateService stateService, TripService tripService, UserService userService) {
        this.stateService = stateService;
        this.tripService = tripService;
        this.userService = userService;
    }
    public String getBotText(){
        return "АБОБАБОБАБОБАБОБАБОБАБОБА";
    }

    public void changeState(long chatID) {
        stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
    }

    public String registerUser(int numberTrip, long chatID){
        if (numberTrip == 0)
        {
            stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
            return "Вы вышли";
        }
        List<Trip> allTrips = tripService.getTripsWithPassengersMoreThanZero();
        Trip trip = allTrips.get(numberTrip - 1);
        User user = new User(chatID);
        tripService.addUserToTrip(trip, user);
        tripService.decrementFreePlaces(trip);
        userService.addTripToUser(trip, user);
        return "Вы записались на поездку: " + trip.getFormattedString();
    }


}
