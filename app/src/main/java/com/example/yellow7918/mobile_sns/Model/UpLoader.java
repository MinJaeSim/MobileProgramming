package com.example.yellow7918.mobile_sns.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpLoader {

    private static final List<User> ourInstance = new ArrayList<>();

    public static List<User> getInstance() {
        return ourInstance;
    }

    private UpLoader() {
    }

    public static User getArticleUploadUser(UUID id) {
        for (User user : ourInstance) {
            if (user.getId().equals(id)) return user;
        }
        return null;
    }
}