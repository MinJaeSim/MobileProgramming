package com.example.yellow7918.mobile_sns;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class User {
    private String name;
    private String id;
    private int textNumber;

    private FirebaseUser user;

    public User(String name) {

        this.name = name;
        textNumber = 0;

        user = FirebaseAuth.getInstance().getCurrentUser();

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
