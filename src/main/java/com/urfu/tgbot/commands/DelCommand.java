package com.urfu.tgbot.commands;

import com.urfu.tgbot.botLogic.MessageSender;
import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class DelCommand {
    private final StateService stateService;
    private final UserService userService;

    private final MessageSender messageSender;

    private final TripService tripService;

    @Autowired
    public DelCommand(StateService stateService, UserService userService, @Lazy MessageSender messageSender, TripService tripService) {
        this.stateService = stateService;
        this.userService = userService;
        this.messageSender = messageSender;
        this.tripService = tripService;
    }

    /**
     * Меняем состояние бота на ожидание команды.
     * @param chatID - индентификатор пользователя.
     */
    public void changeState(Long chatID){stateService.updateState(chatID, States.WAITING_FOR_COMMAND);}

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
        try{
            userService.deleteUser(user);
        }
        catch (Exception e) {return "Не удалось удалить поездку.";}
        user.removeTrip(trip);
        try{
            userService.addUser(user);
        }
        catch (Exception e) {return "Не удалось удалить поездку.";}

        messageSender.sendMessage(trip.getDriverID(),user.getFormattedString() + "отказался от поездки: " + trip.getFormattedString());

        return "Ваша запись на поездку успешно удалена.";
    }
}
