package com.urfu.tgbot.commands;

import com.urfu.tgbot.botLogic.MessageSender;
import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class AddingTrip {
    private final TripService tripService;
    private final StateService stateService;
    private final MessageSender messageSender;

    @Autowired
    private AddingTrip(TripService tripService, StateService stateService, @Lazy MessageSender messageSender) {
        this.tripService = tripService;
        this.stateService = stateService;
        this.messageSender = messageSender;
    }

    /**
     * Добавляет новую поездку в базу данных, включая идентификатор водителя и пункт назначения.
     *
     * @param chatID Идентификатор чата.
     * @param destination Пункт назначения поездки.
     * @return Текст бота.
     * @throws IllegalArgumentException Если пункт назначения не является допустимым институтом.
     * @throws Exception Если возникла ошибка при добавлении поездки в базу данных.
     */
    public String addDriverDestinationTrip(long chatID, String destination) {
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkInstitute(destination);
        }
        catch (IllegalArgumentException exception)
        {
            return exception.getMessage();
        }
        Trip trip = new Trip(chatID, destination);
        try {
            tripService.addTrip(trip);
        } catch (Exception e) {
            tripService.deleteTrip(trip);
            tripService.addTrip(trip);
        }

        stateService.updateState(chatID, States.WAITING_FOR_INPUT_TIME);
        return "Напишите время и дату в формате DD-MM-YY HH:MM.";
    }

    /**
     * Добавляет время и дату поездки в базу данных.
     *
     * @param chatID Идентификатор чата.
     * @param time Время и дата поездки.
     * @return Текст бота.
     * @throws IllegalArgumentException Если формат времени и даты некорректен.
     */
    public String addTimeTrip(long chatID, String time) {
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkDateFormat(time);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
        Trip trip = tripService.getLastTripChatID(chatID);
        Trip newTrip = new Trip.TripBuilder()
                .driverID(chatID)
                .destination(trip.getDestination())
                .timeTrip(time)
                .build();
        tripService.deleteTrip(trip);
        tripService.addTrip(newTrip);
        setTimerForTrip(time, chatID);
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_PLACES);
        return "Введите количество свободных мест.";
    }

    /**
     * Добавляет количество свободных мест в автомобиле в базу данных.
     *
     * @param chatID Идентификатор чата.
     * @param places Количество свободных мест.
     * @return Текст бота.
     * @throws IllegalArgumentException Если количество свободных мест не является допустимым целым числом.
     */
    public String addPlacesTrip(long chatID, String places){
        InputHandler inputHandler = new InputHandler();
        try {
            inputHandler.checkPlaces(places);
        }
        catch (IllegalArgumentException exception){
            return exception.getMessage();
        }
        Trip trip = tripService.getLastTripChatID(chatID);
        Trip newTrip = new Trip.TripBuilder()
                .driverID(chatID)
                .destination(trip.getDestination())
                .timeTrip(trip.getTimeTrip())
                .freePlaces(Integer.parseInt(places))
                .build();
        tripService.deleteTrip(trip);
        tripService.addTrip(newTrip);
        stateService.updateState(chatID, States.WAITING_FOR_COMMAND);
        return "Ваша поездка добавлена в общий список. Для просмотра своих поездок введите /view.\n";
    }

    private void setTimerForTrip(String time, long chatID) {
        // Расчет времени для запуска таймера (через час после указанного времени)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date currentTime = new Date();
        Timer timer = new Timer();
        try {
            Date specifiedTime = simpleDateFormat.parse(time);
            Date timeOneHourLater = new Date(specifiedTime.getTime() + 10); // Добавление часа в миллисекундах
            long delay = timeOneHourLater.getTime() - currentTime.getTime();
            timer.schedule(new TimerTask() {
                public void run() {
                    messageSender.sendMessage(chatID, "aboba");
                }
            }, delay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
