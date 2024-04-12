package com.example.wheatoride.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String email;
    private String fullName;
    private String profilePicUri;
    private Timestamp createdTimestamp;
    private String userId;
    private String fcmToken;
    private String description;

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
}
