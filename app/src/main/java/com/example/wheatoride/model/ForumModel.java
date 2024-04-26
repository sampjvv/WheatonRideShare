package com.example.wheatoride.model;

import com.google.firebase.Timestamp;

public class ForumModel {
    private String username;
    private String description;
    private String numOfSeats;
    private String userId;
    private String location;
    private Timestamp createdTimestamp;


    public ForumModel() {
    }

    public ForumModel( String seats, String description, String location,Timestamp time){
        this.numOfSeats = seats;
        this.location = location;
        this.description = description;
        this.createdTimestamp = time;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation(){ return location;}

    public String getNumOfSeats() {return numOfSeats;}

    public String getUserId () {return userId; }

    public Timestamp getCreatedTimestamp () {return createdTimestamp; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumOfSeats(String numOfSeats) {this.numOfSeats = numOfSeats;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLocation(String location){this.location = location;}

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }


}
