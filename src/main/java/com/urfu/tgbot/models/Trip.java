package com.urfu.tgbot.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue
    private Long id;
    private long driverID;
    private String listPassenger;
    private String destination;
    private String timeTrip;
    private int freePlaces;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tripList")
    private List<User> passengers = new ArrayList<>();

    public Trip(long driverID, String listPassenger, String destination, String timeTrip, int freePlaces) {
        this.driverID = driverID;
        this.listPassenger = listPassenger;
        this.destination = destination;
        this.timeTrip = timeTrip;
        this.freePlaces = freePlaces;
    }


    public Trip(long driverID, String destination) {
        this.driverID = driverID;
        this.destination = destination;
    }

    public Trip() {

    }

    public List<User> getPassengers() {
        return passengers;
    }

    public static class TripBuilder {
        private Long id;
        private long driverID;
        private String listPassenger;
        private String destination;
        private String timeTrip;
        private int freePlaces;


        public TripBuilder driverID(long driverID) {
            this.driverID = driverID;
            return this;
        }

        public TripBuilder listPassenger(String listPassenger) {
            this.listPassenger = listPassenger;
            return this;
        }

        public TripBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public TripBuilder timeTrip(String timeTrip) {
            this.timeTrip = timeTrip;
            return this;
        }

        public TripBuilder freePlaces(int freePlaces) {
            this.freePlaces = freePlaces;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.id = this.id;
            trip.driverID = this.driverID;
            trip.listPassenger = this.listPassenger;
            trip.destination = this.destination;
            trip.timeTrip = this.timeTrip;
            trip.freePlaces = this.freePlaces;
            return trip;
        }
    }


    public long getDriverID() {
        return driverID;
    }

    private void setDriverID(long driver) {
        this.driverID = driver;
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

    public int getFreePlaces() {
        return freePlaces;
    }

    public void addPassenger(User user) {
        this.passengers.add(user);
    }

    public void decrementFreePlaces() {
        this.freePlaces -= 1;
    }

    public String getFormattedString(){
        return destination + timeTrip;
    }

    public void deletePassenger(User user){
        passengers.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
