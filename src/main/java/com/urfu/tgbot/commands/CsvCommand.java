package com.urfu.tgbot.commands;

import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.StateService;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.urfu.tgbot.botLogic.MessageSender;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvCommand {

    private final TripService tripService;

    private final MessageSender messageSender;

    @Autowired
    public CsvCommand(TripService tripService, @Lazy MessageSender messageSender) {
        this.tripService = tripService;
        this.messageSender = messageSender;
    }

    /**
     * Формирует CSV файл с информацией о поездках и отправляет его в сообщении телеграм-боту.
     *
     * @param chatID Идентификатор чата.
     */
    public void generateAndSendCsv(long chatID) {
        List<Trip> trips = tripService.getAllTrips();

        String csvFilePath = "trips.csv";

        try (FileWriter csvWriter = new FileWriter(csvFilePath)) {

            csvWriter.append("Driver id;Time;Trip Destination;Free Places\n");
            for (Trip trip : trips) {
                csvWriter.append(String.format("%s;%s;%s;%d\n", trip.getDriverID(), trip.getTimeTrip(), trip.getDestination(), trip.getFreePlaces()));
            }

            csvWriter.close();

            messageSender.sendFile(chatID, csvFilePath);
        } catch (IOException e) {
        }
    }

}
