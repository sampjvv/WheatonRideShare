package com.example.wheatoride.model;

import com.google.firebase.Timestamp;

public class ForumModel {
    private String username;
    private String location;
    private String seats;
    private String userId;
    private Timestamp createdTimestamp;


    public ForumModel(){
        this.userId = "";
        seats = "";
        location = "";
        username = "";
        createdTimestamp  = null;

    }
    public ForumModel(String location){
        this.userId = "";
        seats = "";
        this.location = location;
        username = "";
        createdTimestamp  = null;

    }

    public ForumModel(String username, String seats, String location, String userId, Timestamp createdTimestamp){
        this.seats = seats;
        this.location = location;
        this.username = username;
        this.userId = userId;
        this.createdTimestamp = createdTimestamp;

    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public String getSeats() {
        return seats;
    }

    public String getUserId () {return userId; }

    public Timestamp getCreatedTimestamp () {return createdTimestamp; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLocation(String location) {
        this.location = location;
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
