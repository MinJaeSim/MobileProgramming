package com.example.yellow7918.mobile_sns.Model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class User {
    private String mName;
    private String mEmail;
    private String mProfile;
    private String mId;
    private int mTextNumber;

    private FirebaseUser user;

    public User(String name) {
        mName = name;
        mTextNumber = 0;

        user = FirebaseAuth.getInstance().getCurrentUser();

        mEmail = user.getEmail();
        mId = user.getUid();

    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setProfile(String profile) {
        mProfile = profile;
    }

    public String getProfile() {
        return mProfile;
    }

    public int getTextNumber() {
        return mTextNumber;
    }

    public void setTextNumber(int num) {
        mTextNumber = num;
    }

    public void increaseTextNumber() {
        mTextNumber++;
    }
}
