package com.example.yellow7918.mobile_sns.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class User {
    private String mName;
    private String mEmail;
    private String mProfile;
    private int mFriendNumber;
    private int mTextNumber;
    private String mId;

    private FirebaseAuth auth;
    private FirebaseUser user;


    public User(String name) {
        mName = name;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mEmail = user.getEmail();
        mId = user.getUid();

    }

    public String getName() {
        return mName;
    }

    public int getFriendNumber() {
        return mFriendNumber;
    }

    public void setFriendNumber(int friendNumber) {
        mFriendNumber = friendNumber;
    }

    public int getTextNumber() {
        return mTextNumber;
    }

    public void setTextNumber(int textNumber) {
        mTextNumber = textNumber;
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
}
