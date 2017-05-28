package com.example.yellow7918.mobile_sns.Model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class User {
    private String name;
    private String email;
    private String profile;
    private String id;
    private int textNumber;

    private FirebaseUser user;

    public User(String name) {

        this.name = name;
        textNumber = 0;

        user = FirebaseAuth.getInstance().getCurrentUser();

        email = user.getEmail();
        id = user.getUid();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public int getTextNumber() {
        return textNumber;
    }

    public void setTextNumber(int num) {
        textNumber = num;
    }

    public void increaseTextNumber() {
        textNumber++;
    }
}
