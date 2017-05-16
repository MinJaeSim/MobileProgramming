package com.example.yellow7918.mobile_sns.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Article {
    private String mName;
    private String mTimestamp;
    private String mImageURL;
    private String mTag;

    public Article() {
        this("", "", "");
    }

    public Article(String name, String timestamp) {
        this(name, timestamp, "");
    }

    public Article(String name, String timestamp, String imageURL) {
        this(name, timestamp, "", "");
    }

    public Article(String name, String timestamp, String imageURL, String tag) {
        mName = name;
        mTimestamp = timestamp();
        mImageURL = imageURL;
        mTag = tag;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public static Article newArticle(String name, String imageURL, String tag) {
        return new Article(name, timestamp(), imageURL, tag);
    }

    private static String timestamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("y년 M월 d일 hh:mm" + " 작성", Locale.KOREA);
        return dateFormat.format(date);
    }

    public String getTimeStamp() {
        return mTimestamp;
    }
}

