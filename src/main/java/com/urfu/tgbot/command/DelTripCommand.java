package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.TripService;
import com.urfu.tgbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DelTripCommand {
    private final TripService tripService;

    private final UserService userService;

    @Autowired
    public DelTripCommand(TripService tripService, UserService userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    /**
     * Удаление поездки.
     * @param chatID индентификатор пользователя.
     * @param tripNumber - Номер поездки, которую нужно удалить.
     * @return Ответ бота.
     */
    public String delTrip(Long chatID, int tripNumber){
        InputHandler inputHandler = new InputHandler();
        List<Trip> tripList = tripService.getAllTripsByChatId(chatID);
        Trip trip = tripList.get(tripNumber - 1);
        try {
            inputHandler.checkNumberBetween(tripNumber, tripList.size());
        }
        catch (Exception e){
            return e.getMessage();
        }
        while (trip.getPassengers() != null && trip.getPassengers().size() > 0) {
            User passenger = trip.getPassengers().get(0);
            try {
                userService.deleteUser(passenger);
            }
            catch (Exception e){
                return "Не удалось удалить поездку";
            }
            passenger.removeTrip(trip);
            try {
                userService.addUser(passenger);
            }
            catch (Exception e){
                return "Не удалось удалить поездку";
            }
        }
        tripService.deleteTrip(trip);
        return "Поездка успешно удалена.";
    }
}
