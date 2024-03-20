package com.example.wheatoride.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String wEmail;
    private String fullName;

    private String email;
    private Timestamp createdTimestamp;
    private String userId;
    private String fcmToken;

    public UserModel() {
    }

    public UserModel(String wEmail, String fullName, Timestamp createdTimestamp, String userId) {
        this.wEmail = wEmail;
        this.fullName = fullName;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
    }

    public String getwEmail() {
        return wEmail;
    }

    public void setwEmail(String wEmail) {
        this.wEmail = wEmail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) { this.email = email;}

    public String getEmail(){return this.email;}

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
}
