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

    /**
     * Получает все доступные поездки из базы данных.
     *
     * @return Список доступных поездок.
     */
    public String getAllAvailableTrips() {
        List<Trip> trips = tripService.getTripsWithPassengersMoreThanZero();

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

    /**
     * Обновляет состояние чата на WAITING_FOR_INPUT_TRIP_NUMBER.
     *
     * @param chatID Идентификатор чата.
     */
    public void changeState(long chatID) {
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_TRIP_NUMBER);
    }

}
