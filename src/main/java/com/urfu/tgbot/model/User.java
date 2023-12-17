package com.urfu.tgbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Класс сущности, представляющий пользователя Telegram-бота.
 */
@Entity
@Table(name = "users")
public class User {

    private String username;

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

    public long getChatID() {
        return chatID;
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
