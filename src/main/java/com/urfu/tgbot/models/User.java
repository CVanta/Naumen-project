package com.urfu.tgbot.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    private String username;


    private String tgUsername;

    private boolean isActive;

    @Id
    private long chatID;

    private String institute;

    private long phoneNumber;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Trip> tripList;

    public User() {
    }

    public User(long chatID) {
        this.chatID = chatID;
    }

    public User(String username, long chatID, String tgUsername) {
        this.username = username;
        this.chatID = chatID;
        this.tgUsername = tgUsername;
    }

    /**
     * Билдер для пользователя. Собирает пользователя по частям,
     * по мере того как он вводит данные.
     */
    public static class Builder {
        private String username;
        private long chatID;
        private String institute;
        private long phoneNumber;

        private String tgUsername;

        public Builder(long chatID) {
            this.chatID = chatID;
        }

        /**
         * Устанавливает имя пользователя.
         * @param username имя пользователя
         * @return Builder
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Устанавливает TGимя пользователя.
         * @param tgUsername TGимя пользователя
         * @return Builder
         */
        public Builder tgUsername(String tgUsername){
            this.tgUsername = tgUsername;
            return this;
        }

        /**
         * Устанавливает институт пользователя.
         * @param institute институт пользователя.
         * @return Builder
         */
        public Builder institute(String institute) {
            this.institute = institute;
            return this;
        }

        /**
         * Устанавливает номер телефона пользователя.
         * @param phoneNumber номер телефона пользователя.
         * @return Builder
         */
        public Builder phoneNumber(long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User build() {
            User user = new User();
            user.username = this.username;
            user.chatID = this.chatID;
            user.institute = this.institute;
            user.phoneNumber = this.phoneNumber;
            user.tgUsername = this.tgUsername;
            return user;
        }
    }

    /**
     * Возвращает отформатированную строковую representation информации о пользователе.
     * @return Отформатированная строковая representation информации о пользователе.
     */
    public String getFormattedString() {
        return " ФИО: " + username + "\n Номер телефона: " + phoneNumber + "\n Институт: " + institute;
    }

    /**
     * Возвращает список поездок пользователя.
     * @return Список поездок на которые записан пользователь.
     */
    public List<Trip> getTripList() {
        return tripList;
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
     * Возвращает Telegram-имя пользователя.
     *
     * @return Telegram-имя пользователя.
     */
    public String getTgUsername() {
        return tgUsername;
    }


    /**
     * Возвращает номер телефона пользователя.
     *
     * @return Номер телефона пользователя.
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Устанавливает активен ли пользователь.
     *
     * @param active True- пользователь активен, False - пользователь не активен
     */
    public void setActive(boolean active) {
        this.isActive = active;
    }


    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return Уникальный идентификатор пользователя.
     */
    public long getChatID() {
        return this.chatID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return chatID == user.chatID;
    }

    @Override
    public int hashCode() {
        return (int) (chatID ^ (chatID >>> 32));
    }
}
