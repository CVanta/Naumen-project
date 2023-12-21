package com.urfu.tgbot.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс сущности, представляющий пользователя Telegram-бота.
 */
@Entity
@Table(name = "users")
public class User {

    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Trip> tripList;

    private String tgUsername;

    @Id
    private long chatID;

    private String institute;

    private String phoneNumber;

    public User() {
    }

    public User(long chatID) {
        this.chatID = chatID;
    }

    public User(String username, long chatID) {
        this.username = username;
        this.chatID = chatID;
    }

    public User(String username, long chatID, String institute, String phoneNumber) {
        this.username = username;
        this.chatID = chatID;
        this.institute = institute;
        this.phoneNumber = phoneNumber;
    }

    public User(String username, String tgUsername, long chatID, String phoneNumber) {
        this.username = username;
        this.tgUsername = tgUsername;
        this.chatID = chatID;
        this.phoneNumber = phoneNumber;
    }

    public User(String username, long chatID, String tgUsername) {
        this.username = username;
        this.chatID = chatID;
        this.tgUsername = tgUsername;
    }

    public String getInstitute() {
        return institute;
    }

    /**
     * Возвращает номер телефона пользователя.
     *
     * @return Номер телефона пользователя.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Добавление поездки к пользователю.
     * @param trip поездка которую надо добавить.
     */
    public void addTrip(Trip trip) {
        if (this.tripList == null)
            tripList = new ArrayList<>();
        this.tripList.add(trip);
        trip.getPassengers().add(this);
    }

    /**
     * Удаление поездки у пользователя.
     * @param trip поездка которую надо удалить.
     */
    public void removeTrip(Trip trip) {
        if (this.tripList == null) {
            this.tripList = new ArrayList<>();
            return;
        }
        this.tripList.remove(trip);
        trip.getPassengers().remove(this);
    }

    /**
     * Возвращает список поездок пользователя
     * @return список поездок пользователя
     */
    public List<Trip> getTripList() {
        return tripList;
    }

    /**
     * Возвращает отформатированную строковую representation информации о пользователе.
     * @return Отформатированная строковая representation информации о пользователе.
     */
    public String getFormattedString() {
        return " ФИО: " + username + "\n Номер телефона: " + phoneNumber + "\n Институт: " + institute;
    }

    /**
     * Возвращает полное имя пользователя.
     *
     * @return Полное имя пользователя.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return Уникальный идентификатор пользователя.
     */
    public long getChatID() {
        return chatID;
    }

    /**
     * Возвращает Telegram-имя пользователя.
     *
     * @return Telegram-имя пользователя.
     */
    public String getTgUsername() {
        return tgUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return chatID == user.chatID;
    }

    @Override
    public int hashCode() {
        return (int) (chatID ^ (chatID >>> 32));
    }
}