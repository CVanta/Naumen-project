package com.urfu.tgbot.commands;

import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileCommand {

    private final UserService userService;

    @Autowired
    public ProfileCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получает список всех поездок, на которые пользователь записался.
     *
     * @param chatID Идентификатор чата.
     * @return Список поездок.
     */
    public String viewTrips(long chatID){
        User user = userService.getUserByChatID(chatID);
        int cnt = 1;
        StringBuilder result = new StringBuilder();
        result.append("Поездки, на которые вы записались \n");
        for (Trip trip : user.getTripList()) {
            result
                    .append(cnt)
                    .append(". ")
                    .append(trip.getFormattedString())
                    .append("\n");
            cnt +=1;
        }
        return result.toString();
    }
}
