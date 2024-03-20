package com.example.wheatoride.model;

import android.net.Uri;

import com.google.firebase.Timestamp;

public class UserModel {
    private String wEmail;
    private String fullName;

    private String email;

    private String profilePicUri;
    private Timestamp createdTimestamp;
    private String userId;
    private String fcmToken;

    public UserModel() {
    }



    public UserModel(String wEmail, String fullName, String profilePicUri, Timestamp createdTimestamp, String userId) {
        this.wEmail = wEmail;
        this.fullName = fullName;
        this.profilePicUri = profilePicUri;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
    }

    public String getProfilePicUri() {
        return profilePicUri;
    }
    public void setProfilePicUri(String profilePicUri) {
        this.profilePicUri = profilePicUri;
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
