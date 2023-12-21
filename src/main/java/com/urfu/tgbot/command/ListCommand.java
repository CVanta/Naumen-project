package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс предоставляет обработку команды /list
 */
@Component
public class ListCommand {

    private final TripService tripService;


    @Autowired
    public ListCommand(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * Получает все доступные поездки из базы данных.
     *
     * @return Список доступных поездок.
     */
    public String getAllAvailableTrips() {
        List<Trip> trips = tripService.getAvailableTrips();

        StringBuilder result = new StringBuilder();
        int num = 1;
        for (Trip trip : trips) {
            String tString = trip.getTimeTrip() + " " +
                    trip.getDestination() + " мест:" +
                    trip.getFreePlaces();
            result.append(num).append(". ").append(tString).append("\n");
            num += 1;
        }
        result.append("0 - для выхода в режим комманд");
        return result.toString();
    }
}
