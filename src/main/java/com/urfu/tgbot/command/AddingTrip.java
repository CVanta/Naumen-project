package com.urfu.tgbot.command;

import com.urfu.tgbot.enumeration.StateEnum;
import com.urfu.tgbot.model.Trip;
import com.urfu.tgbot.service.StateService;
import com.urfu.tgbot.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Класс для обработки команды добавление поездки
 */
@Component
public class AddingTrip {
    private final TripService tripService;
    private final StateService stateService;

    @Autowired
    private AddingTrip(TripService tripService, StateService stateService) {
        this.tripService = tripService;
        this.stateService = stateService;
    }

    /**
     * Добавляет новую поездку в базу данных, включая идентификатор водителя и пункт назначения.
     *
     * @param chatID Идентификатор чата.
     * @param destination Пункт назначения поездки.
     * @return Текст бота.
     * @throws IllegalArgumentException Если пункт назначения не является допустимым институтом.
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
        tripService.addTrip(trip);
        stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_TIME);
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
        try {
            Trip trip = tripService.getLastTripChatID(chatID);
            Trip newTrip = new Trip(chatID, trip.getDestination(), time);
            tripService.addTrip(newTrip);
            stateService.updateState(chatID, StateEnum.WAITING_FOR_INPUT_PLACES);
            return "Введите количество свободных мест.";
        }
        catch (Exception e) {
            return "Не удалось добавить дату и время";
        }
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
        try {
            Trip trip = tripService.getLastTripChatID(chatID);
            Trip newTrip = new Trip(chatID, trip.getDestination(),trip.getTimeTrip(), Integer.parseInt(places));
            tripService.addTrip(newTrip);
            stateService.updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
            return "Ваша поездка добавлена в общий список. Для просмотра своих поездок введите /view.\n";
        }
        catch (Exception e){
            return "Не удалось добавить колличество свободных мест";
        }
    }
}
