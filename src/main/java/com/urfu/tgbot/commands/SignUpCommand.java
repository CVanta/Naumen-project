package com.urfu.tgbot.commands;

import com.urfu.tgbot.botLogic.Keyboard;
import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

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

    /**
     * Обновляет состояние чата на WAITING_FOR_COMMAND.
     *
     * @param chatID Идентификатор чата.
     */
    public void changeState(long chatID) {
        stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
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
            stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
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
        userService.addTripToUser(trip, user);
        changeState(chatID);
        return "Вы записались на поездку: " + trip.getFormattedString();
    }

    public ReplyKeyboardMarkup getTripNumbersKeyboard(){
        Keyboard keyboard =  new Keyboard();
        return keyboard.getTripNumbersKeyboard(tripService.getAvailableTrips().size());
    }
}
