package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DelTripCommand {
    private StateService stateService;
    private TripService tripService;

    @Autowired
    public void DelTripCommand(StateService stateService, TripService tripService){
        this.stateService = stateService;
        this.tripService = tripService;
    }

    /**
     * Меняем состояние бота на ожидание команды.
     * @param chatID - индентификатор пользователя.
     */
    public void changeState(long chatID){stateService.updateState(chatID, States.WAITING_FOR_COMMAND);}

    /**
     * Удаление поездки.
     * @param chatID индентификатор пользователя.
     * @param tripNumber - Номер поездки, которую нужно удалить.
     * @return Ответ бота.
     */
    public String delTrip(Long chatID, int tripNumber){
        List<Trip> tripList = tripService.getAllTripsByChatId(chatID);
        Trip trip = tripList.get(tripNumber - 1);
        tripService.deleteTrip(trip);
        return "Поездка успешно удалена.";
    }
}
