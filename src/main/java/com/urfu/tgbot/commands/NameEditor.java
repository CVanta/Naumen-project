package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.User;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NameEditor {
    private final StateService stateService;
    private final UserService userService;

    @Autowired
    public NameEditor(StateService stateService, UserService userService) {
        this.stateService = stateService;
        this.userService = userService;
    }

    public String editName(long chatID, String name) throws Exception {
        User user = new User(name, Long.toString(chatID));
        try {
            userService.addUser(user);
        }
        catch (Exception e){
            userService.deleteUser(user);
            userService.addUser(user);
        }
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_INSTITUTE);
        return "Ваше имя успешно установлено.";
    }


}
