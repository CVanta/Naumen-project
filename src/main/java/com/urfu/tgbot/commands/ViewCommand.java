package com.urfu.tgbot.commands;

import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.urfu.tgbot.enums.States;

import java.util.List;

@Component
public class ViewCommand {

    private StateService stateService;
    private TripService tripService;

    @Autowired
    public void ViewCommand(StateService stateService, TripService tripService) {
        this.stateService = stateService;
        this.tripService = tripService;
    }

    public void changeState(long chatID){
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_SHOW_OR_DEL);
    }

    public String getBotText(long chatID){
        List<Trip> trips = tripService.getAllTripsByChatId(chatID);
        String result = "";
        int num = 1;
        for (Trip trip : trips) {
            String tString = trip.getTimeTrip() + " " +
                    trip.getDestination() + " мест:" +
                    trip.getFreePlaces();
            result += num + ". " + tString + "\n";
            num += 1;
        }
        result += "0 - для выхода в режим комманд";
        return result;
    }
}
