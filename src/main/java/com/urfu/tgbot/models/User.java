package com.urfu.tgbot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getFormattedString() {
        return " ФИО: " + username + "\n Номер телефона: " + phoneNumber + "\n Институт: " + institute;
    }

    public String getUsername() {
        return username;
    }

    public long getChatID() {
        return this.chatID;
    }

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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder institute(String institute) {
            this.institute = institute;
            return this;
        }

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
