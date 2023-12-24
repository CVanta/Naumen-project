package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.model.User;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.TripService;
import com.urfu.tgbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс предоставляет обработку команды /SignUp
 */
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

    /**
     * Регистрирует пользователя на указанную поездку.
     *
     * @param numberTrip Номер поездки.
     * @param chatID     Идентификатор чата.
     * @return Текст бота.
     */
    public String registerUser(int numberTrip, long chatID) {
        if (numberTrip == 0) {
            stateService.updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
            return "Вы вышли";
        }
        List<Trip> allTrips = tripService.getAvailableTrips();
        if (allTrips.size() < numberTrip)
            return "Номера поездки нет в списке";
        Trip trip = allTrips.get(numberTrip - 1);
        if (trip.getDriverID() == chatID)
            return "Вы не можете записаться на свою поездку!";
        User user = userService.getUserByChatID(chatID);
        if (user.getTripList().contains(trip)) {
            return "Вы не можете записаться на поездку дважды!";
        }
        tripService.addUserToTrip(trip, user);
        try {
            userService.addTripToUser(trip, user);
        }
        catch (Exception e){
            return "Не удалось записаться на поездку";
        }
        stateService.updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
        return "Вы записались на поездку: " + trip.getFormattedString();
    }


}
