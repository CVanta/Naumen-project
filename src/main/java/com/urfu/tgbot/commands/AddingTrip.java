package com.urfu.tgbot.commands;

import com.urfu.tgbot.enums.States;
import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddingTrip {
    TripService tripService;
    StateService stateService;

    @Autowired
    private AddingTrip(TripService tripService, StateService stateService) {
        this.tripService = tripService;
        this.stateService = stateService;
    }

    public String addDriverDestinationTrip(long chatID, String destination) {
        InputHandler inputHandler = new InputHandler();
        inputHandler.checkInstitute(destination);
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
        stateService.updateState(chatID, States.WAITING_FOR_INPUT_PLACES);
        return "Введите количество свободных мест.";
    }

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
}
