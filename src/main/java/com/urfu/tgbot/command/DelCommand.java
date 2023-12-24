package com.urfu.tgbot.command;

import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.TripService;
import com.urfu.tgbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Класс для обработки команды отмена записи на поездку
 */
@Component
public class DelCommand {
    private final UserService userService;

    private final TripService tripService;

    @Autowired
    public DelCommand(UserService userService, TripService tripService) {
        this.userService = userService;
        this.tripService = tripService;
    }

    /**
     * Отменяет запись пользователя на поездку.
     * @param chatID индентификатор пользователя.
     * @param tripNumber номер поездки.
     * @return Ответ бота.
     */
    public String delTrip(Long chatID, int tripNumber){
        InputHandler inputHandler = new InputHandler();
        User user = userService.getUserByChatID(chatID);
        try {
            inputHandler.checkNumberBetween(tripNumber, user.getTripList().size());
        }
        catch (Exception e){
            return e.getMessage();
        }
        Trip trip = user.getTripList().get(tripNumber - 1);
        trip.incrementFreePlaces();
        tripService.addTrip(trip);
        user.removeTrip(trip);
        userService.addUser(user);

        return "Ваша запись на поездку успешно удалена.";
    }
}