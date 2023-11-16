package com.urfu.tgbot.models;

import com.urfu.tgbot.enums.Role;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    private String username;
    private boolean isActive;
    private String chatID;

    private String institute;

    private long phoneNumber;
    @Id
    @GeneratedValue
    private Long id;

    public User() {
    }

    public User(String username, String chatID) {
        this.username = username;
        this.chatID = chatID;
        this.institute = null;
        this.phoneNumber = 0;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();


    public String getUsername() {
        return username;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public String  getChatID() {
        return this.chatID;
    }
}
