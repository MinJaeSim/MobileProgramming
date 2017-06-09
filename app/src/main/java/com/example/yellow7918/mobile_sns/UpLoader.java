package com.example.yellow7918.mobile_sns;

import java.util.ArrayList;
import java.util.List;

public class UpLoader {

    private static final List<User> ourInstance = new ArrayList<>();

    public static List<User> getInstance() {
        return ourInstance;
    }

    private UpLoader() {
    }

    public static User getArticleUploadUser(String id) {
        for (User user : ourInstance) {
            if (user.getName().equals(id)) return user;
        }
        return null;
    }
}