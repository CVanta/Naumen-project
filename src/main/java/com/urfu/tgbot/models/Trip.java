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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        if (driverID != trip.driverID) return false;
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (driverID ^ (driverID >>> 32));
        return result;
    }

    /**
     * Билдер для поездки. Собирает поездку по частям,
     * по мере того как пользователь вводит данные.
     */
    public static class TripBuilder {
        private Long id;
        private long driverID;
        private String listPassenger;
        private String destination;
        private String timeTrip;
        private int freePlaces;


        /**
         * Устанавливаем driverID для водителя поездки.
         * @param driverID - идентификатор водителя поездки
         * @return TripBuilder
         */
        public TripBuilder driverID(long driverID) {
            this.driverID = driverID;
            return this;
        }
        /**
         * Устанавливаем список пассажиров в поездке.
         * @param listPassenger - список пассажиров.
         * @return TripBuilder
         */
        public TripBuilder listPassenger(String listPassenger) {
            this.listPassenger = listPassenger;
            return this;
        }
        /**
         * Устанавливаем место назначения в поездке.
         * @param destination - место назначения.
         * @return TripBuilder
         */
        public TripBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }
        /**
         * Устанавливаем время начала поездки.
         * @param timeTrip - время поездки.
         * @return TripBuilder
         */
        public TripBuilder timeTrip(String timeTrip) {
            this.timeTrip = timeTrip;
            return this;
        }
        /**
         * Устанавливаем кол-во свободных мест в автомобиле.
         * @param freePlaces - кол-во свободных мест в автомобиле.
         * @return TripBuilder
         */
        public TripBuilder freePlaces(int freePlaces) {
            this.freePlaces = freePlaces;
            return this;
        }

        /**
         * Билдер для поездки
         * @return Trip - возвращает собрануню поездку.
         */
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

    /**
     * Возвращает идентификатор водителя.
     *
     * @return Идентификатор водителя.
     */
    public long getDriverID() {
        return driverID;
    }

    /**
     * Возвращает пункт назначения поездки.
     *
     * @return Пункт назначения поездки.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Возвращает время поездки.
     *
     * @return Время поездки.
     */
    public String getTimeTrip() {
        return timeTrip;
    }

    /**
     * Возвращает идентификатор поездки.
     *
     * @return Идентификатор поездки.
     */
    public Long getId() {
        return id;
    }

    /**
     * Возвращает кол-во свободных мест в автомобиле.
     *
     */
    public int getFreePlaces() {
        return freePlaces;
    }

    /**
     * Добавляет пассажира к поездке.
     *
     * @param user пассажир, которого нужно добавить.
     */
    public void addPassenger(User user) {
        this.passengers.add(user);
    }

    /**
     * Уменьшает кол-во свободных мест в автомобиле на единицу.
     *
     */
    public void decrementFreePlaces() {
        this.freePlaces -= 1;
    }
    public void incrementFreePlaces() {
        this.freePlaces += 1;
    }

    public String getFormattedString(){
        return destination + timeTrip;
    }

    /**
     * Удаляет пассажира из поездки.
     *
     * @param user пассажир, которого нужно удалить.
     */
    public void deletePassenger(User user){
        passengers.remove(user);
    }


}
