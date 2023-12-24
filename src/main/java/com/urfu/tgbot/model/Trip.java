package com.urfu.tgbot.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс сущности, представляющий поездку.
 */
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue
    private Long id;
    private long driverID;
    private String destination;
    private String timeTrip;
    private int freePlaces;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tripList")

    private List<User> passengers = new ArrayList<>();


    public Trip(long driverID){
        this.driverID = driverID;
    }
    public Trip(long driverID, String destination){
        this.driverID = driverID;
        this.destination = destination;
    }
    public Trip(long driverID, String destination, String timeTrip){
        this.driverID = driverID;
        this.destination = destination;
        this.timeTrip = timeTrip;
    }
    public Trip(long driverID, String destination, String timeTrip, int freePlaces){
        this.driverID = driverID;
        this.destination = destination;
        this.timeTrip = timeTrip;
        this.freePlaces = freePlaces;
    }

    public Trip() {

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
     * Возвращает список пассажиров
     * @return список пассажиров
     */
    public List<User> getPassengers() {
        return passengers;
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
     */
    public void decrementFreePlaces() {
        this.freePlaces -= 1;
    }

    /**
     * Увеличивает кол-во свободных мест в автомобиле на единицу.
     */
    public void incrementFreePlaces() {
        this.freePlaces += 1;
    }

    /**
     * Возвращает отформатированную строковую representation информации о пользователе.
     * @return Отформатированная строковая representation информации о пользователе.
     */
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

}
