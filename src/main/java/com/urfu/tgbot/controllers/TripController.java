package com.urfu.tgbot.controllers;

import com.urfu.tgbot.models.Trip;
import com.urfu.tgbot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/allTrips")
    @ResponseBody
    public List<Trip> getTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping(value = "/save/{driver}/{listPassenger}/{destination}/{date}")
    @ResponseBody
    public Trip save(@PathVariable String driver, @PathVariable String listPassenger, @PathVariable String destination, @PathVariable String date) {
        return tripService.saveTrip(driver, listPassenger, destination, date);
    }


}
