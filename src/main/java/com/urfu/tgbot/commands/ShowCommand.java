package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ShowCommand {
    private StateService stateService;
    private TripService tripService;
    @Autowired
    public void ShowCommand(StateService stateService, TripService tripService){
        this.stateService = stateService;
        this.tripService = tripService;
    }

    public void changeState(long chatID){
        stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
    }

    public String getBotText(long chatID, int number){
        List<Trip> tripList = tripService.getAllTripsByChatId(chatID);
        Trip trip = tripList.get(number-1);
        StringBuilder result = new StringBuilder();
        int cnt = 1;
        for (User passenger : trip.getPassengers()) {
            result.append(cnt)
                    .append(". ")
                    .append("@")
                    .append(passenger.getTgUsername())
                    .append(" ")
                    .append(passenger.getUsername())
                    .append(passenger.getPhoneNumber())
                    .append(" ")
                    .append("\n");
        }
        return result.toString();
    }
}
