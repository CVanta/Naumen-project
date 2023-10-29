package com.example.TG_BOT.models;

public class Passenger {

    private  String name;
    private  String group;
    private String tgNickname;

    public Passenger(String name, String group, String tgNickname) {
        this.name = name;
        this.group = group;
        this.tgNickname = tgNickname;
    }

    public String getName() {
        return name;
    }

    private  void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    private  void setGroup(String group) {
        this.group = group;
    }

    public String getTgNickname() {
        return tgNickname;
    }

    private  void setTgNickname(String tgNickname) {
        this.tgNickname = tgNickname;
    }
}
