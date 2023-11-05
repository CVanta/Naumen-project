package com.example.TG_BOT.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Trip2GreatComeback")
public class Trip {

    @Id
    @GeneratedValue
    private Long id;
    private String driver;
    private String listPassenger;
    private String destination;
    private String timeTrip;


    public Trip(String driver, String listPassenger, String destination, String timeTrip) {
        this.driver = driver;
        this.listPassenger = listPassenger;
        this.destination = destination;
        this.timeTrip = timeTrip;
    }

    public Trip() {

    }

    public String getDriver() {
        return driver;
    }

    private void setDriver(String driver) {
        this.driver = driver;
    }

    public String getListPassenger() {
        return listPassenger;
    }

    private void setListPassenger(String listPassenger) {
        this.listPassenger = listPassenger;
    }

    public String getDestination() {
        return destination;
    }

    private void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTimeTrip() {
        return timeTrip;
    }

    private void setTimeTrip(String timeTrip) {
        this.timeTrip = timeTrip;
    }

    public Long getId() {
        return id;
    }
}
