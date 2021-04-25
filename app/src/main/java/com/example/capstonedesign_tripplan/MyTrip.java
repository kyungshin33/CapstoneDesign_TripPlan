package com.example.capstonedesign_tripplan;

import java.util.List;

public class MyTrip {
    private String userName;
    private String title;
    private String content;
    private List<String> images;

    public MyTrip(String userName, String title, String content, List<String> images) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
