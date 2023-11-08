package com.example.TG_BOT.commands;


import org.springframework.stereotype.Component;

@Component
public class StartCommand{

    public String getBotText(){
        return """
                Здравствуйте. Вас приветствует бот для поиска попутчиков.
                Давайте зарегистрируемся. Введите ваше ФИО.
                """;
    }
}
