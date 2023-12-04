package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileCommand {

    private final UserService userService;
    private final StateService stateService;
    @Autowired
    public ProfileCommand(UserService userService, StateService stateService) {
        this.userService = userService;
        this.stateService = stateService;
    }

    /**
     * Получает список всех поездок, на которые пользователь записался.
     *
     * @param chatID Идентификатор чата.
     * @return Список поездок.
     */
    public String viewTrips(long chatID){
        User user = userService.getUserByChatID(chatID);
        List<Trip> trips = user.getTripList();
        if(trips.size() == 0){
            stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
            return "Вы не записаны ни на одну поездку";
        }
        int cnt = 1;
        StringBuilder result = new StringBuilder();
        result.append("Поездки, на которые вы записались \n");
        for (Trip trip : trips) {
            result
                    .append(cnt)
                    .append(". ")
                    .append(trip.getFormattedString())
                    .append("\n");
            cnt +=1;
        }
        result.append("0 - для выхода в режим комманд");
        return result.toString();
    }

    public void changeState(Long chatID){stateService.updateState(chatID, States.WAITING_FOR_INPUT_DEL);}
}
