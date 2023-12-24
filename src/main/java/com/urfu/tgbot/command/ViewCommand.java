package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Класс, реализующий команду /view - вывод поездок которые создал данный пользователь.
 */

@Component
public class ViewCommand {
    private final TripService tripService;

    @Autowired
    public ViewCommand(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * Получает текст для бота, содержащий информацию о поездках для указанного чата.
     *
     * @param chatID Идентификатор чата пользователя.
     * @return Текст для бота с информацией о поездках.
     */
    public String getBotText(long chatID){
        List<Trip> trips = tripService.getAllTripsByChatId(chatID);
        StringBuilder result = new StringBuilder();
        int num = 1;
        for (Trip trip : trips) {
            String tString = trip.getTimeTrip() + " " +
                    trip.getDestination() + " мест:" +
                    trip.getFreePlaces();
            result.append(num).append(". ").append(tString).append("\n");
            num += 1;
        }
        result.append("0 - для выхода в режим команд");
        return result.toString();
    }
}

