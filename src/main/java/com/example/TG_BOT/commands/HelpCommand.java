package com.example.TG_BOT.commands;

import org.springframework.stereotype.Component;

@Component
public class HelpCommand{

    public String getBotCommand(){
        return """
                Список доступных команд:<
                >'/start'<
                >'/add'<
                >'/edit'<
                >'/help'<
                >'/list'<
                """;
    }

}
