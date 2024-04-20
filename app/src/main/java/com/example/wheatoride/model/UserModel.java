package com.example.wheatoride.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String email;
    private String fullName;
    private String phoneNumber;
    private String profilePicUri;
    private Timestamp createdTimestamp;
    private String userId;
    private String fcmToken;
    private String description;
    private boolean isDriver;
    private String vehicleModel;
    private String vehicleNumSeats;
    private String vehicleDescription;

    public UserModel() {

    }



    public UserModel(String wEmail, String fullName, String profilePicUri, Timestamp createdTimestamp, String userId, String description) {
        this.email = wEmail;
        this.fullName = fullName;
        this.profilePicUri = profilePicUri;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.description = description;
    }

    public UserModel(String wEmail, String fullName, String profilePicUri, Timestamp createdTimestamp, String userId) {
        this.email = wEmail;
        this.fullName = fullName;
        this.profilePicUri = profilePicUri;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.description = "";
        this.isDriver = false;
        this.vehicleModel = "";
        this.vehicleNumSeats = "";
        this.vehicleDescription = "";
    }
    public String getProfilePicUri() {
        return profilePicUri;
    }

    public void setProfilePicUri(String profilePicUri) {
        this.profilePicUri = profilePicUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDescription() {
        return description;
    }


    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleNumSeats() {
        return vehicleNumSeats;
    }

    public void setVehicleNumSeats(String vehicleNumSeats) {
        this.vehicleNumSeats = vehicleNumSeats;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
