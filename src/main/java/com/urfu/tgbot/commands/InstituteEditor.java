package com.urfu.tgbot.commands;

import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstituteEditor {
    private final StateService stateService;
    private final UserService userService;
    @Autowired
    public InstituteEditor(StateService stateService, UserService userService) {
        this.stateService = stateService;
        this.userService = userService;
    }

//    public String editInstitute(long ChatID, String institute)
//    {
//        //User user =
//    }





}
