package com.example.yellow7918.mobile_sns.Model;

import android.util.Log;

public class Article {
    private String name;
    private String timeStamp;
    private String imageURL;
    private String tag;

    public Article() {
        this("", "", "","");
    }

    public Article(String name, String timestamp) {
        this(name, timestamp, "");
    }

    public Article(String name, String timestamp, String imageURL) {
        this(name, timestamp, imageURL, "");
    }

    public Article(String name, String timestamp, String imageURL, String tag) {
        this.name = name;
        timeStamp = timestamp;
        this.imageURL = imageURL;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTag() {
        return tag;
    }

    public static Article newArticle(String name, String timestamp, String imageURL, String tag) {
        return new Article(name, timestamp, imageURL, tag);
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}

