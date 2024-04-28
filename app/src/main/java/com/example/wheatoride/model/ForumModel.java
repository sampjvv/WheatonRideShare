package com.example.wheatoride.model;

import com.google.firebase.Timestamp;

import java.util.Map;

public class ForumModel {
    private String username;
    private String description;
    private String numOfSeats;
    private String userId;
    private String location;
    private String postTimeStamp;
    private String isConfirmed;


    public ForumModel() {
    }

    public ForumModel( String seats, String description, String location,String time){
        this.numOfSeats = seats;
        this.location = location;
        this.description = description;
        this.postTimeStamp = time;
        isConfirmed = "false";
    }

    public ForumModel(Map<String,Object> map){
        this.numOfSeats = map.get("numOfSeats").toString();
        this.location = map.get("location").toString();
        this.description = map.get("description").toString();
        this.postTimeStamp = map.get("postTimeStamp").toString();
        isConfirmed = "false";
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

    public String getPostTimeStamp() {return postTimeStamp; }

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

    public void setPostTimeStamp(String postTimeStamp) {
        this.postTimeStamp = postTimeStamp;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String confirmed) {
        isConfirmed = confirmed;
    }
}
