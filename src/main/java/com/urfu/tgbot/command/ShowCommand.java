package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ShowCommand {
    private final TripService tripService;
    @Autowired
    public ShowCommand(TripService tripService){
        this.tripService = tripService;
    }

    /**
     * Возвращает список пассажиров для указанной поездки.
     *
     * @param chatID Идентификатор чата.
     * @param number Номер поездки, которую нужно показать.
     * @return Текст бота.
     */
    public String getBotText(long chatID, int number){
        List<Trip> tripList = tripService.getAllTripsByChatId(chatID);
        Trip trip = tripList.get(number-1);
        StringBuilder result = new StringBuilder();
        List<User> users = trip.getPassengers();
        if(users.size() == 0) return "К вам никто не записался на поездку.";
        int cnt = 1;
        for (User passenger : users) {
            result.append(cnt)
                    .append(". ")
                    .append("@")
                    .append(passenger.getTgUsername())
                    .append(" ")
                    .append(passenger.getUsername())
                    .append(" ")
                    .append(passenger.getPhoneNumber())
                    .append("\n");
            cnt +=1;
        }
        return result.toString();
    }
}
