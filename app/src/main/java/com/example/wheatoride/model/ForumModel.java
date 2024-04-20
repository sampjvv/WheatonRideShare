package com.example.wheatoride.model;

import com.google.firebase.Timestamp;

public class ForumModel {
    private String username;
    private String description;
    private String seats;
    private String userId;
    private Timestamp createdTimestamp;


    public ForumModel(){
        this.userId = "";
        seats = "";
        description = "";
        username = "";
        createdTimestamp  = Timestamp.now();

    }
    public ForumModel(String description){
        this.userId = "";
        seats = "";
        this.description = description;
        username = "";
        createdTimestamp  = Timestamp.now();

    }

    public ForumModel(String username, String seats, String description, String userId, Timestamp createdTimestamp){
        this.seats = seats;
        this.description = description;
        this.username = username;
        this.userId = userId;
        this.createdTimestamp = createdTimestamp;

    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public String getSeats() {
        return seats;
    }

    public String getUserId () {return userId; }

    public Timestamp getCreatedTimestamp () {return createdTimestamp; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLocation(String description) {
        this.description = description;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
