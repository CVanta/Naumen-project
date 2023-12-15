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

    private long phoneNumber;

    public User() {
    }

    public User(long chatID) {
        this.chatID = chatID;
    }

    public User(String username, long chatID) {
        this.username = username;
        this.chatID = chatID;
    }

    public String getInstitute() {
        return institute;
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
        return this.chatID;
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

        public Builder(long chatID) {
            this.chatID = chatID;
        }


        public Builder chatID(long chatID) {
            this.chatID = chatID;
            return this;
        }


        public long getPhoneNumber() {
            return phoneNumber;
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
            return user;
        }
    }
}
