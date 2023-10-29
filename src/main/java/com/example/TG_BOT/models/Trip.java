package com.example.TG_BOT.models;

import java.util.Date;
import java.util.List;

public class Trip {
    private Driver driver;
    private List<Passenger> listPassenger;
    private String destination;
    private Date timeTrip;

    public Trip(Driver driver, List<Passenger> listPassenger, String destination, Date timeTrip) {
        this.driver = driver;
        this.listPassenger = listPassenger;
        this.destination = destination;
        this.timeTrip = timeTrip;
    }

    public Driver getDriver() {
        return driver;
    }

    private void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Passenger> getListPassenger() {
        return listPassenger;
    }

    private void setListPassenger(List<Passenger> listPassenger) {
        this.listPassenger = listPassenger;
    }

    public String getDestination() {
        return destination;
    }

    private void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getTimeTrip() {
        return timeTrip;
    }

    private void setTimeTrip(Date timeTrip) {
        this.timeTrip = timeTrip;
    }
}
